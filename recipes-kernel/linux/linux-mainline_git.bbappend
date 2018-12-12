FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "5e79dbf6be495f5cace82efa9b0b876814bf6447"
PV = "v4.18.0-rc6+git${SRCPV}"
