FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "86dad54f215de9657f5c06f4e22291d1791eb2ce"
PV = "v4.18.0-rc6+git${SRCPV}"
