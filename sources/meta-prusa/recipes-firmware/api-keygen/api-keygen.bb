DESCRIPTION="SL1 Apikey generator service"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI = "\
	file://api-keygen.sh \
	file://api-keygen.service \
"

inherit systemd

RDEPENDS:${PN} += "bash coreutils grep"

do_install:append () {
	# Keygen service
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/api-keygen.service ${D}${systemd_system_unitdir}/
	
	# Keygen script
	install -d ${D}${bindir}
	install --mode 755 ${WORKDIR}/api-keygen.sh ${D}${bindir}
	
	# Ensure key dir exists
	install -d ${D}${sysconfdir}/sl1fw
}

SYSTEMD_SERVICE:${PN} = "api-keygen.service"
