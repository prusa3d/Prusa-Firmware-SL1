FILESEXTRAPATHS_prepend := "${THISDIR}/atf:"
LIC_FILES_CHKSUM = "file://license.rst;md5=e927e02bca647e14efd87e9e914b2443"

SRC_URI = "git://github.com/ARM-software/arm-trusted-firmware.git;branch=master"
SRCREV = "793c38f0fa0e17345a03181a28c5358627d0e4d9"

PLATFORM_sun50i = "sun50i_a64"

EXTRA_OEMAKE_append = " LD=${TARGET_PREFIX}ld.bfd"
