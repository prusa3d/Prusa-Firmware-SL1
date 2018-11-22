FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "8dfdf24b017bd243b59690ddab304c96d5a73053"
PV = "v4.18.0-rc6+git${SRCPV}"
