DESCRIPTION="Service that restarts avahi on hostname change"

LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"
LICENSE = "GPLv3"

SRC_URI = "\
	file://avahi-restart.path \
	file://avahi-restart.service \
	file://GPLv3.patch \
"

inherit systemd

PACKAGES = "${PN}"

do_install () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/avahi-restart.path ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/avahi-restart.service ${D}${systemd_system_unitdir}/
}

SYSTEMD_SERVICE_${PN} = "avahi-restart.path avahi-restart.service"
