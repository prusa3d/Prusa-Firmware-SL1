FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
	file://brcmfmac43362-sdio.txt \
"

do_install_append() {
	mkdir -p ${D}/usr/lib/firmware/brcm
	install -m 0644 $_firmware ${WORKDIR}/brcmfmac43362-sdio.txt ${D}/usr/lib/firmware/brcm
}

FILES_${PN}-bcm43362 += " \
	/usr/lib/firmware/brcm/brcmfmac43362-sdio.txt \
"
