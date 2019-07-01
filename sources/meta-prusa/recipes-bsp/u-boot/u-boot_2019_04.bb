require u-boot_common.inc

SRC_URI = "git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/u-boot.git;protocol=ssh;branch=master"
SRCREV = "5b894134ac7316f1c1b96409bd0fd4209438e5ce"

PV = "v2019.04+git${SRCPV}"
PE = "2"
