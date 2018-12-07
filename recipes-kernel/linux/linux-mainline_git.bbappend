FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "d8be83b4eb316408ed5ce3ac1ea9c42c69016619"
PV = "v4.18.0-rc6+git${SRCPV}"
