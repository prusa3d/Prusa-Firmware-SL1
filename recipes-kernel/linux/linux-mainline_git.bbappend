FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "e8d3b9e9a8c49ba9377505b2fde2e3552337819e"
PV = "v4.18.0-rc6+git${SRCPV}"
