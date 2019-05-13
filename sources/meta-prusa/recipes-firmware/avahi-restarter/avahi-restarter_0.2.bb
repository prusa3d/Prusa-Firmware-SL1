LICENSE = "CLOSED"

SRC_URI = "\
	file://avahi-restart.path \
	file://avahi-restart.service \
"

inherit systemd

PACKAGES = "${PN}"

do_install () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/avahi-restart.path ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/avahi-restart.service ${D}${systemd_system_unitdir}/
}

SYSTEMD_SERVICE_${PN} = "avahi-restart.path avahi-restart.service"
