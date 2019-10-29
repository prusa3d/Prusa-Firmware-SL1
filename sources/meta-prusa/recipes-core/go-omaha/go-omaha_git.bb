DESCRIPTION = "omaha protocol for go"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=d2794c0df5b907fdace235a619d80314"

SRC_URI = "git://git@${GO_IMPORT};protocol=ssh;branch=master"
#SRCREV = "aad98348623aa93a3a67260d1e945348d932b554"
SRCREV = "c6e611f1a1f6efddfef20e9d5ca216c254fa7656"
UPSTREAM_CHECK_COMMITS = "1"

PACKAGES += "${PN}-serve-package"

GO_IMPORT = "gitlab.com/prusa3d/${BPN}"
#GO_INSTALL = "${GO_IMPORT}"

inherit go systemd

FILES_${PN} = "\
	${bindir}/updater \
	${datadir}/dbus-1/system.d/cz.prusa3d.Updater1.conf \
"
FILES_${PN}-serve-package = "${bindir}/serve-package"

do_install_append() {
	install -d ${D}${datadir}/dbus-1/system.d
	install -m 0644 ${S}/src/${GO_IMPORT}/_release/cz.prusa3d.Updater1.conf ${D}${datadir}/dbus-1/system.d/
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${S}/src/${GO_IMPORT}/_release/updater.service ${D}${systemd_system_unitdir}
}

SYSTEMD_SERVICE_${PN} = "updater.service"
