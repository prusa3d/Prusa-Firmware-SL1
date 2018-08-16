FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"

SRC_URI="\
	git://git@github.com/zavorka/linux-sunxi.git;protocol=ssh;branch=a64-de2-v3.1 \
	file://defconfig \
"

SRCREV_pn-${PN} = "bea88ac941e9b1cd42f07588f0f8f142540fa65b"
PV = "v4.18.0-rc6+git${SRCPV}"
