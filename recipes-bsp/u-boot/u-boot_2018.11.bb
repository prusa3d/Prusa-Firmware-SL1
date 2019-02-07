require u-boot_common.inc

SRC_URI = "git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/u-boot.git;protocol=ssh;branch=master"
SRCREV = "5e20eeea8995b29efa62877fff72f3421c40f542"

PV = "v2018.11-rc1+git${SRCPV}"
PE = "2"
