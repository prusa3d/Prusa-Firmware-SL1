DESCRIPTION="infinite eMMC test"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI = "\
	file://run_emmctest.sh \
	file://emmctest.service \
"

inherit systemd

RDEPENDS:${PN} += "bash e2fsprogs-badblocks"

do_install:append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/emmctest.service ${D}${systemd_system_unitdir}/
	install -d ${D}${bindir}
	install --mode 755 ${WORKDIR}/run_emmctest.sh ${D}${bindir}
}

SYSTEMD_SERVICE:${PN} = "emmctest.service"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"
