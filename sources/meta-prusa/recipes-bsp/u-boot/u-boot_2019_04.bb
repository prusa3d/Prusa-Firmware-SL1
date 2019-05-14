require u-boot_common.inc

SRC_URI = "git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/u-boot.git;protocol=ssh;branch=master"
SRCREV = "be284204a1da82331ba6a6c1b1876ca5ce176052"

PV = "v2019.04+git${SRCPV}"
PE = "2"
