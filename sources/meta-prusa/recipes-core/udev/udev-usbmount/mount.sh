#!/bin/sh
#
# Called from udev
#
# Attempt to mount any added block devices and umount any removed devices

MOUNT="/usr/bin/systemd-mount"
UMOUNT="/usr/bin/systemd-umount"
GID=`awk -F':' '/^projects/{print $3}' /etc/group`

for line in `grep -h -v ^# @nonarch_base_libdir@/udev/mount.blacklist @nonarch_base_libdir@/udev/mount.blacklist.d/*`
do
	if [ ` expr match "$DEVNAME" "$line" ` -gt 0 ];
	then
		logger "udev/mount.sh" "[$DEVNAME] is blacklisted, ignoring"
		exit 0
	fi
done

automount_systemd() {
    name="`basename "$DEVNAME"`"
    mount_point="/run/media/root/${name}"
    [ -d ${mount_point} ] || mkdir -p ${mount_point}

    case $ID_FS_TYPE in
    vfat|fat)
        # If filesystemtype is vfat, change the ownership group to 'disk', and
        # grant it with  w/r/x permissions.
        #MOUNT="$MOUNT -o umask=007,gid=`awk -F':' '/^disk/{print $3}' @sysconfdir@/group`"
        # TODO
        MOUNT="$MOUNT -o utf8,gid=$GID,dmask=007,fmask=117"
        ;;
    *)
        MOUNT="$MOUNT -o gid=$GID,dmask=007,fmask=117"
        ;;
    esac

    if ! $MOUNT --no-block -t auto --automount=yes --timeout-idle-sec=1s $DEVNAME ${mount_point}
    then
        rm_dir ${mount_point}
    else
        logger "mount.sh/automount" "Auto-mount of [${mount_point}] successful"
        touch "/tmp/.automount-$name"
    fi
}

rm_dir() {
	# We do not want to rm -r populated directories
	if test "`find "$1" | wc -l | tr -d " "`" -lt 2 -a -d "$1"
	then
		! test -z "$1" && rm -r "$1"
	else
		logger "mount.sh/automount" "Not removing non-empty directory [$1]"
	fi
}

name="`basename "$DEVNAME"`"

if [ "$ACTION" = "add" ] && [ -n "$DEVNAME" ] && [ -n "$ID_FS_TYPE" ]; then
    # Note the root filesystem can show up as /dev/root in /proc/mounts,
    # so check the device number too
    if expr $MAJOR "*" 256 + $MINOR != `stat -c %d /`; then
        automount_systemd
    fi
fi

if [ "$ACTION" = "remove" ] || [ "$ACTION" = "change" ] && [ -x "$UMOUNT" ] && [ -n "$DEVNAME" ]; then
    for mnt in `cat /proc/mounts | grep "$DEVNAME" | cut -f 2 -d " " `
    do
        $UMOUNT $mnt
    done

    # Remove empty directories from auto-mounter
    name="`basename "$DEVNAME"`"
    test -e "/tmp/.automount-$name" && rm_dir ${mount_point}
fi
