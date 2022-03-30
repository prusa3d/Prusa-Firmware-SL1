SUMMARY = "Printer model detector"
DESCRIPTION = "Systemd service started early in bootup to detect and expose printer model in /run/model."

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI = "\
	file://model-detect.service \
	file://model-detect.sh \
"

inherit systemd

do_install:append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/model-detect.service ${D}${systemd_system_unitdir}/
	install -d ${D}${bindir}
	install --mode 755 ${WORKDIR}/model-detect.sh ${D}${bindir}
}

SYSTEMD_SERVICE:${PN} = "model-detect.service"