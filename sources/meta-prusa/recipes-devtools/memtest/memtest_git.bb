DESCRIPTION="infinite RAM memory test"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI = "\
	file://run_memtest.sh \
	file://memtest.service \
"

inherit systemd

RDEPENDS:${PN} += "memtester bash"

do_install:append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/memtest.service ${D}${systemd_system_unitdir}/
	install -d ${D}${bindir}
	install --mode 755 ${WORKDIR}/run_memtest.sh ${D}${bindir}
}

SYSTEMD_SERVICE:${PN} = "memtest.service"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"
