SUMMARY = "Update channel and keyring switch script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

RDEPENDS:${PN} = "bash"

SRC_URI = "\
	file://set-update-channel.sh \
	file://update-selector.service \
"

inherit systemd

do_install() {
	# Script
	install -d ${D}${sbindir}
	install -m 0755 ${WORKDIR}/set-update-channel.sh ${D}/${sbindir}/set-update-channel.sh

	# State
	install -d ${D}${sysconfdir}
	echo "stable" > ${D}${sysconfdir}/update_channel

	# Service
	install -d ${D}${systemd_system_unitdir}
	install --mode 644 ${WORKDIR}/update-selector.service ${D}${systemd_system_unitdir}/update-selector.service
}

FILES:${PN} = "\
	${sysconfdir}/update_channel \
	${sbindir}/set-update-channel.sh \
"

SYSTEMD_SERVICE:${PN} = "update-selector.service"
