SUMMARY = "firmware for motion controller board (SL-CONTROLLER)"
HOMEPAGE = "https://gitlab.com/prusa3d/sl1/mc-fw"
SECTION = "firmware"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI = "git://git@gitlab.com/prusa3d/sl1/mc-fw.git;protocol=ssh;branch=master"
SRCREV:pn-${PN} = "1ab5b31099fa9ec39cac20f7720487289511b0bd"
S = "${WORKDIR}/git/SLA-control-01"

HOST_SYS = "avr"

inherit avr-toolchain-base

# vim-native provides xxd
DEPENDS = " \
	avr-libc-native \
	vim-native \
"

FILES:${PN} += "${libdir}/firmware/*.hex"

do_install() {
	install -d ${D}${nonarch_base_libdir}/firmware
	install -m 0644 ${B}/SLA-control_rev06.hex ${D}${nonarch_base_libdir}/firmware/
}

