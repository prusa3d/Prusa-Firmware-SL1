require u-boot_common.inc

SRC_URI = "git://git@gitlab.com/prusa3d/distro/u-boot.git;protocol=ssh;branch=master"
SRCREV = "5b894134ac7316f1c1b96409bd0fd4209438e5ce"

PV = "v2019.04+git${SRCPV}"
PE = "2"
