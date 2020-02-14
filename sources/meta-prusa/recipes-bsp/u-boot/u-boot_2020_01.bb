require u-boot_common.inc

inherit python3native

SRC_URI = "git://github.com/u-boot/u-boot.git;protocol=http;tag=v2020.01"

PV = "v2020.01+git${SRCPV}"
PE = "3"
