require u-boot_common.inc

SRC_URI = "git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/u-boot.git;protocol=ssh;branch=master"
SRCREV = "52589d41ec2c863896f5925246bf073a97e647bd"

PV = "v2019.01+git${SRCPV}"
PE = "2"
