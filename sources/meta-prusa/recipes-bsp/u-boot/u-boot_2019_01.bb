require u-boot_common.inc

SRC_URI = "git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/u-boot.git;protocol=ssh;branch=master"
SRCREV = "7a6792027c42d85068167a42c6c34e8dc6f9c879"

PV = "v2019.01+git${SRCPV}"
PE = "2"
