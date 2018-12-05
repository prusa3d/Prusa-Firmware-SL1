FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=master \
	file://defconfig \
	file://edt-touch-release-hotfix.patch \
"

SRCREV_pn-${PN} = "debb47ecc392224d39d2cd03a0cc7c6235e4afb9"
PV = "v4.18.0-rc6+git${SRCPV}"
