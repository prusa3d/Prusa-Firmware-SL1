DESCRIPTION="SL1 Apikey generator service"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "\
	file://api-keygen.sh \
	file://api-keygen.service \
	file://GPLv3.patch \
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
