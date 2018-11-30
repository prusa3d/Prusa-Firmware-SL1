FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "4a18c0d3f88769fa10c04fcb6606f0e1f2393997"
PV = "v4.18.0-rc6+git${SRCPV}"
