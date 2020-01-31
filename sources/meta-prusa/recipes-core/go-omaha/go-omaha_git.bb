DESCRIPTION = "omaha protocol for go"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=d2794c0df5b907fdace235a619d80314"

SRC_URI = " \
	git://git@${GO_IMPORT};protocol=ssh;branch=master \
	file://updater.service \
"
SRCREV = "9bec842486cbc7799eb90649ffeb763051171c7b"
UPSTREAM_CHECK_COMMITS = "1"

PACKAGES += "${PN}-serve-package"

GO_IMPORT = "gitlab.com/prusa3d/${BPN}"
#GO_INSTALL = "${GO_IMPORT}"

inherit go systemd

FILES_${PN} = "\
	${bindir}/updater \
	${datadir}/dbus-1/system.d/cz.prusa3d.Updater1.conf \
	${libdir}/systemd/system \
"
FILES_${PN}-serve-package = "${bindir}/serve-package"

do_install_append() {
	install -d ${D}${datadir}/dbus-1/system.d
	install -m 0644 ${S}/src/${GO_IMPORT}/_release/cz.prusa3d.Updater1.conf ${D}${datadir}/dbus-1/system.d/
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/updater.service ${D}${systemd_system_unitdir}

	# Enable service
	install -d ${D}${systemd_system_unitdir}/multi-user.target.wants
	ln -s ${systemd_system_unitdir}/updater.service ${D}${systemd_system_unitdir}/multi-user.target.wants/
}

SYSTEMD_SERVICE_${PN} = "updater.service"
