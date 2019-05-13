require u-boot_common.inc

SRC_URI = "git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/u-boot.git;protocol=ssh;branch=master"
SRCREV = "fb58f6eb1566165e7630b1a03aa90b4b40bdc12c"

PV = "v2018.11-rc1+git${SRCPV}"
PE = "2"
