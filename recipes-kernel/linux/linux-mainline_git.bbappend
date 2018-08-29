FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "bea88ac941e9b1cd42f07588f0f8f142540fa65b"
PV = "v4.18.0-rc6+git${SRCPV}"
