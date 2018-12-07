FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "2b4705acc73c7ec27ba722a9229eadfffeb5d087"
PV = "v4.18.0-rc6+git${SRCPV}"
