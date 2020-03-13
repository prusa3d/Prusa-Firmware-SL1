FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " \
	file://0001-accept-bin-as-a-valid-suffix-for-emmc-bootloader-update.patch \
	file://0002-when-extcsd-rw-fails-try-once-more-before-bailing-out.patch \
	file://0003-fix-omitted-boot-ack-bit-during-extcsd-update.patch \
	file://0004-load-config-from-usr-share.patch \
	file://system.conf \
	file://ca-prod.cert.pem \
	file://ca-dev.cert.pem \
"

do_install_append () {
    install -d ${D}${datadir}/rauc/
    install -m 0644 ${WORKDIR}/system.conf ${D}${datadir}/rauc/system.conf
    install -m 0644 ${WORKDIR}/ca-prod.cert.pem ${D}${datadir}/rauc/ca-prod.cert.pem
    install -m 0644 ${WORKDIR}/ca-prod.cert.pem ${D}${sysconfdir}/rauc/ca-prod.cert.pem
    install -m 0644 ${WORKDIR}/ca-dev.cert.pem ${D}${datadir}/rauc/ca-dev.cert.pem
    install -m 0644 ${WORKDIR}/ca-dev.cert.pem ${D}${sysconfdir}/rauc/ca-dev.cert.pem
    install -m 0644 ${WORKDIR}/ca-prod.cert.pem ${D}${sysconfdir}/rauc/ca.cert.pem
}
