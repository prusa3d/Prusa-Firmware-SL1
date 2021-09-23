/* SPDX-License-Identifier: LGPL-2.1+ */
#include <errno.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <getopt.h>
#include <grp.h>
#include <sys/socket.h>
#include <sys/stat.h>

#include <systemd/sd-bus.h>
#include <systemd/sd-device.h>
#include <systemd/sd-journal.h>

#define USEC_PER_SEC  ((uint64_t) 1000000ULL)

#define log_error_errno(r, msg, ...)    ({ sd_journal_print(LOG_ERR, msg ": %s", ## __VA_ARGS__, strerror(abs(r))); r;})
#define log_error(msg...)               sd_journal_print(LOG_ERR, msg)
#define log_info(msg...)                sd_journal_print(LOG_INFO, msg)

#define _cleanup_(x) __attribute__((__cleanup__(x)))
#define _cleanup_free_ _cleanup_(freep)
static inline void freep(void *p) { free(*(void**) p); }

#define TAKE_PTR(ptr) ({ typeof(ptr) _ptr_ = (ptr); (ptr) = NULL; _ptr_; })
#define RET_IF_ERR(x) ({int _r = x; if (_r < 0) return _r;})

static char *arg_mount_what = NULL;
static char *arg_mount_where = NULL;
static char *arg_mount_type = NULL;
static char *arg_mount_options = NULL;
static uint64_t arg_timeout_idle = 3*USEC_PER_SEC;
static gid_t arg_gid = -1;

static char *arg_mount_what_escaped = NULL;
static char *arg_mount_where_escaped = NULL;

const char *bus_error_message(const sd_bus_error *e, int error) {
        if (e) {
                if (sd_bus_error_has_name(e, SD_BUS_ERROR_ACCESS_DENIED)) return "Access denied";
                if (e->message) return e->message;
        }
        return strerror(abs(error));
}

static int parse_argv(int argc, char *argv[]) {
        enum { ARG_OWNER = 1 };
        static const struct option options[] = { { "owner", required_argument, NULL, ARG_OWNER }, {} };

        int c;
        struct group *g;

        while ((c = getopt_long(argc, argv, "o:", options, NULL)) >= 0)
                if (c == ARG_OWNER) {
                        errno = 0;
                        g = getgrnam(optarg);
                        if (!g) {
                                return log_error_errno(errno ? errno : -ESRCH, "Cannot use \"%s\" as owner", optarg);
                        }
                        arg_gid = g->gr_gid;
                }

        if (argc != optind + 1)
                return log_error("Exactly one positional argument required.");

        arg_mount_what = strdup(argv[optind]);

        return 0;
}

static char const* const uid_gid_fs_types[] = { "fat", "msdos", "ntfs", "vfat", NULL };
static bool fs_type_can_uid_gid(const char *fs_type) {
        char const* const* p = uid_gid_fs_types;
        while (*p)
                if (strcmp(*p++, fs_type) == 0) return true;
        return false;
}

static int transient_unit_set_properties(sd_bus_message *m) {
        _cleanup_free_ char *device_unit = NULL;

        asprintf(&device_unit, "%s.device", arg_mount_what_escaped);
        RET_IF_ERR(sd_bus_message_append(m, "(sv)(sv)",
                                  "After", "as", 1, device_unit,
                                  "BindsTo", "as", 1, device_unit));

        RET_IF_ERR(sd_bus_message_append(m, "(sv)", "CollectMode", "s", "inactive-or-failed"));

        return 0;
}

static int transient_mount_set_properties(sd_bus_message *m) {

        _cleanup_free_ char *fsck = NULL;
        asprintf(&fsck, "systemd-fsck@%s.service", arg_mount_what_escaped);
        RET_IF_ERR(sd_bus_message_append(m,
                                  "(sv)(sv)",
                                  "Requires", "as", 1, fsck,
                                  "After", "as", 1, fsck));

        RET_IF_ERR(transient_unit_set_properties(m));

        RET_IF_ERR(sd_bus_message_append(m, "(sv)", "What", "s", arg_mount_what));
        RET_IF_ERR(sd_bus_message_append(m, "(sv)", "Type", "s", arg_mount_type));
        RET_IF_ERR(sd_bus_message_append(m, "(sv)", "Options", "s", arg_mount_options));

        return 0;
}

static int transient_automount_set_properties(sd_bus_message *m) {
        RET_IF_ERR(transient_unit_set_properties(m));

        _cleanup_free_ char *rmdir_on_exit = NULL;
        asprintf(&rmdir_on_exit, "rmdir-on-exit@%s.service", arg_mount_where_escaped);
        RET_IF_ERR(sd_bus_message_append(m,
                                  "(sv)(sv)",
                                  "Wants", "as", 1, rmdir_on_exit,
                                  "After", "as", 1, rmdir_on_exit));

        RET_IF_ERR(sd_bus_message_append(m, "(sv)", "TimeoutIdleUSec", "t", arg_timeout_idle));
        return 0;
}

#define BUS(x) ({int _r = x; if (_r < 0) return log_error_errno(_r, "Failed to create bus message"); })

static int reset_failed_unit(sd_bus *bus, const char *unit) {
	_cleanup_(sd_bus_message_unrefp) sd_bus_message *m = NULL;

	BUS(sd_bus_message_new_method_call(bus, &m, "org.freedesktop.systemd1",
					   "/org/freedesktop/systemd1",
					   "org.freedesktop.systemd1.Manager",
					   "ResetFailedUnit"));
	BUS(sd_bus_message_set_allow_interactive_authorization(m, false));
	BUS(sd_bus_message_append(m, "s", unit));

	return sd_bus_call(bus, m, 0, NULL, NULL);
}

static int start_transient_automount(sd_bus *bus, sd_bus_error *error) {
        _cleanup_(sd_bus_message_unrefp) sd_bus_message *m = NULL;
        _cleanup_free_ char *automount_unit = NULL, *mount_unit = NULL;
        int r;

        asprintf(&automount_unit, "%s.automount", arg_mount_where_escaped);
        asprintf(&mount_unit, "%s.mount", arg_mount_where_escaped);

        reset_failed_unit(bus, mount_unit);
        reset_failed_unit(bus, automount_unit);

        BUS(sd_bus_message_new_method_call(bus, &m, "org.freedesktop.systemd1",
                                                       "/org/freedesktop/systemd1",
                                                       "org.freedesktop.systemd1.Manager",
                                                       "StartTransientUnit"));
        BUS(sd_bus_message_set_allow_interactive_authorization(m, false));

        /* Name and mode */
        BUS(sd_bus_message_append(m, "ss", automount_unit, "fail"));
        /* Properties */
        BUS(sd_bus_message_open_container(m, 'a', "(sv)"));
        BUS(transient_automount_set_properties(m));
        BUS(sd_bus_message_close_container(m));

        /* Auxiliary units */
        BUS(sd_bus_message_open_container(m, 'a', "(sa(sv))"));
        BUS(sd_bus_message_open_container(m, 'r', "sa(sv)"));
        BUS(sd_bus_message_append(m, "s", mount_unit));
        BUS(sd_bus_message_open_container(m, 'a', "(sv)"));
        BUS(transient_mount_set_properties(m));
        BUS(sd_bus_message_close_container(m));

        BUS(sd_bus_message_close_container(m));
        BUS(sd_bus_message_close_container(m));

        r = sd_bus_call(bus, m, 0, error, NULL);
        if (r < 0)
                return log_error_errno(r, "Failed to start transient automount unit: %s", bus_error_message(error, r));
        return 0;
}

static int acquire_mount_options(sd_device *d) {
        _cleanup_free_ char *options = NULL;
        const char *v;
        int r;
        bool is_fat = sd_device_get_property_value(d, "ID_FS_TYPE", &v) == 0 && strcmp(v, "vfat") == 0;

        if (arg_gid != (uid_t) -1 && fs_type_can_uid_gid(arg_mount_type))
                asprintf(&options, ",gid=%" PRIu32 ",dmask=0007,fmask=0137", arg_gid);

        r = asprintf(&arg_mount_options, "ro%s%s", options ? options : "", is_fat ? ",flush" : "");

        return r < 0 ? -ENOMEM : 0;
}

static int discover_device(void) {
        _cleanup_(sd_device_unrefp) sd_device *d = NULL;
        struct stat st;
        const char *v;
        int r;

        if (stat(arg_mount_what, &st) < 0)
                return log_error_errno(errno, "Can't stat %s", arg_mount_what);

        if (!S_ISBLK(st.st_mode))
                return log_error("Invalid file type: %s", arg_mount_what);

        r = sd_device_new_from_devnum(&d, 'b', st.st_rdev);
        if (r < 0)
                return log_error_errno(r, "Failed to get device from device number");

        if (sd_device_get_property_value(d, "ID_FS_USAGE", &v) < 0 || strcmp(v, "filesystem") != 0)
                return log_error("%s does not contain a known file system.", arg_mount_what);

        RET_IF_ERR(sd_device_get_property_value(d, "ID_FS_TYPE", &v));
        arg_mount_type = strdup(v);

        asprintf(&arg_mount_where, "/run/media/system/%s", basename(arg_mount_what));
        asprintf(&arg_mount_where_escaped, "run-media-system-%s", basename(arg_mount_what));
        asprintf(&arg_mount_what_escaped, "dev-%s", basename(arg_mount_what));

        RET_IF_ERR(acquire_mount_options(d));

        return 0;
}

static int bus_check_peercred(sd_bus *c) {
        socklen_t n = sizeof(struct ucred);
        struct ucred u;
        int fd, r;

        fd = sd_bus_get_fd(c);
        if (fd < 0)
                return fd;

        r = getsockopt(fd, SOL_SOCKET, SO_PEERCRED, &u, &n);
        if (r < 0)
                return -errno;
        if (n != sizeof(struct ucred))
                return -EIO;
        if (u.uid != 0 && u.uid != geteuid())
                return -EPERM;

        return 1;
}

static int bus_connect_system_systemd(sd_bus **_bus) {
        _cleanup_(sd_bus_close_unrefp) sd_bus *bus = NULL;
        /* We are root,so let's talk directly to the system
         * instance, instead of going via the bus */
        RET_IF_ERR(sd_bus_new(&bus));
        RET_IF_ERR(sd_bus_set_address(bus, "unix:path=/run/systemd/private"));
        RET_IF_ERR(sd_bus_start(bus));
        RET_IF_ERR(bus_check_peercred(bus));
        *_bus = TAKE_PTR(bus);
        return 0;
}

int main(int argc, char* argv[]) {
        _cleanup_(sd_bus_unrefp) sd_bus *bus = NULL;
        _cleanup_(sd_bus_error_free) sd_bus_error error = SD_BUS_ERROR_NULL;

        int r;

        RET_IF_ERR(parse_argv(argc, argv));

        r = bus_connect_system_systemd(&bus);
        if (r < 0)
                return log_error_errno(r, "Failed to connect to bus");

        r = discover_device();
        if (r < 0)
                return log_error_errno(r, "Failed to gather properties of the device");

        r = start_transient_automount(bus, &error);
        if (r < 0)
                log_error("%s: %s", error.name, error.message);

        return 0;
}
