do_install_append() {
	ln -sr ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43362-sdio.cubietech,cubietruck.txt ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43362-sdio.${DISTRO},${MACHINE}.txt
}
