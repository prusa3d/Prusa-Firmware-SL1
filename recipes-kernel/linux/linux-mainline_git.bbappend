FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "d967a09bd03f5df11f0797000fd90ee697ff5a6d"
PV = "v4.18.0-rc6+git${SRCPV}"
