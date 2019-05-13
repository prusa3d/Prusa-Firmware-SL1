LICENSE = "CLOSED"

SRC_URI = "\
	file://api-keygen.sh \
	file://api-keygen.service \
	file://prusa-auth.conf \
"

inherit systemd

RDEPENDS_${PN} += "bash coreutils grep"

do_install_append () {
	# Keygen service
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/api-keygen.service ${D}${systemd_system_unitdir}/
	
	# Keygen script
	install -d ${D}${bindir}
	install --mode 755 ${WORKDIR}/api-keygen.sh ${D}${bindir}
	
	# Nginx htdigest configuration
	install -d ${D}${sysconfdir}/nginx
	install --mode 755 ${WORKDIR}/prusa-auth.conf ${D}${sysconfdir}/nginx/
	
	# Ensure key dir exists
	install -d ${D}${sysconfdir}/sl1fw
}

SYSTEMD_SERVICE_${PN} = "api-keygen.service"
