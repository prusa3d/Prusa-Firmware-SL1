FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "34e5cbec92dd87cd336e79f3440fc95596dc4c04"
PV = "v4.18.0-rc6+git${SRCPV}"
