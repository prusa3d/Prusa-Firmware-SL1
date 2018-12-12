LICENSE = "CLOSED"

SRC_URI = "\
	file://sla-splash.service \
	file://prusa-sla-splash.fbimg.lzma \
"

inherit systemd

DEPENDS += "bash"

FILES_${PN} = "${datadir}/splash"

do_install () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-splash.service ${D}${systemd_system_unitdir}/
	install -d ${D}${datadir}/splash/
	install --mode 644 ${WORKDIR}/prusa-sla-splash.fbimg.lzma ${D}${datadir}/splash/prusa-sla-splash.fbimg.lzma
}

SYSTEMD_SERVICE_${PN} = "sla-splash.service"
