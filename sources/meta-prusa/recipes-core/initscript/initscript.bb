SUMMARY = "Basic init script that ensures all essential paths are mounted"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://init"
S = "${WORKDIR}"

do_install() {
	install -d ${D}${sbindir}
	install -m 0755 ${WORKDIR}/init ${D}${sbindir}/init
}

inherit allarch

FILES:${PN} += "${sbindir}/init"
