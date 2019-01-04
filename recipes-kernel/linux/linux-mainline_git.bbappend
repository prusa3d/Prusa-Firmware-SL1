FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "2c00502c352ffd05dc8ae2ec04877017f43669e3"
PV = "v4.18.0-rc6+git${SRCPV}"
