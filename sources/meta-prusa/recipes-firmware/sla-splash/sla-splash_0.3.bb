LICENSE = "CLOSED"

SRC_URI = "\
	file://sla-splash.service \
	file://prusa-sla-splash.fbimg.xz;unpack=false \
"

inherit systemd

DEPENDS += "bash xz"

FILES_${PN} = "${datadir}/splash"

do_install () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-splash.service ${D}${systemd_system_unitdir}/
	install -d ${D}${datadir}/splash/
	install --mode 644 ${WORKDIR}/prusa-sla-splash.fbimg.xz ${D}${datadir}/splash/prusa-sla-splash.fbimg.xz
}

SYSTEMD_SERVICE_${PN} = "sla-splash.service"
