FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "6f43002d79eeec5223d6d11e5b148e0d0ecad663"
PV = "v4.18.0-rc6+git${SRCPV}"
