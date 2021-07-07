FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

PACKAGES =+ "${PN}-bcm43456"
FILES:${PN}-bcm43456 = "${nonarch_base_libdir}/firmware/brcm/brcmfmac43456-sdio.*"
LICENSE:${PN}-bcm43456 = "Firmware-broadcom_bcm43xx"
RDEPENDS:${PN}-bcm43456 += "${PN}-broadcom-license"

SRC_URI:append = " \
	file://brcmfmac43456-sdio.bin \
	file://brcmfmac43456-sdio.AP6256.txt \
"

do_install:append() {
	ln -s brcmfmac43362-sdio.cubietech,cubietruck.txt ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43362-sdio.prusa,prusa64-sl1.txt

	ln -s brcmfmac43455-sdio.MINIX-NEO\ Z83-4.txt ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43455-sdio.prusa,prusa64-sl2.txt

	install -m 644 ${WORKDIR}/brcmfmac43456-sdio.bin ${D}${nonarch_base_libdir}/firmware/brcm/
	install -m 644 ${WORKDIR}/brcmfmac43456-sdio.AP6256.txt ${D}${nonarch_base_libdir}/firmware/brcm/
	ln -s brcmfmac43456-sdio.AP6256.txt ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43456-sdio.prusa,prusa64-sl2.txt
}
