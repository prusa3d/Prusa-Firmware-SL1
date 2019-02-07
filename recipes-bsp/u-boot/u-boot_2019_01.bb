require u-boot_common.inc

SRC_URI = "git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/u-boot.git;protocol=ssh;branch=master"
SRCREV = "602c2f68a3b058052725141f9d38a9c81680b06c"

PV = "v2019.01+git${SRCPV}"
PE = "2"
