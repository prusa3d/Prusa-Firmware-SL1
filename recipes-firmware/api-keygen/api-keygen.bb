LICENSE = "CLOSED"

SRC_URI = "\
	file://api-keygen.sh \
	file://api-keygen.service \
"

inherit systemd

RDEPENDS_${PN} += "bash"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/api-keygen.service ${D}${systemd_system_unitdir}/
	
	install -d ${D}${bindir}
	install --mode 755 ${WORKDIR}/api-keygen.sh ${D}${bindir}

	# Ensure key dir exists
	install -d ${D}${sysconfdir}/sl1fw
}

SYSTEMD_SERVICE_${PN} = "api-keygen.service"
