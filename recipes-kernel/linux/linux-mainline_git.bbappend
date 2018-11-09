FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "b8cef516caf5f83c2eb9a9f840db8a7e65cad82d"
PV = "v4.18.0-rc6+git${SRCPV}"
