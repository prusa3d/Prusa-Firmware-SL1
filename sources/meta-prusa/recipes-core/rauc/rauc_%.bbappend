FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " \
	file://0001-accept-bin-as-a-valid-suffix-for-emmc-bootloader-update.patch \
	file://0002-when-extcsd-rw-fails-try-once-more-before-bailing-out.patch \
	file://0003-fix-omitted-boot-ack-bit-during-extcsd-update.patch \
	file://0004-load-config-from-usr-share.patch \
	file://system.conf \
"

do_install_append () {
    install -d ${D}${datadir}/rauc/
    install -m 0644 ${WORKDIR}/system.conf ${D}${datadir}/rauc/system.conf
    install -m 0644 ${WORKDIR}/${RAUC_KEYRING_FILE} ${D}${datadir}/rauc/ca.cert.pem
}
