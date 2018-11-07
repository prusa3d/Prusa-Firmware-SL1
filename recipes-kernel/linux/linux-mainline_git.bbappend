FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
"

SRCREV_pn-${PN} = "5b1fd67c9e605414b09fc75581b12220b3196a7c"
PV = "v4.18.0-rc6+git${SRCPV}"
