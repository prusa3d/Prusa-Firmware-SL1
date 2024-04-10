SUMMARY = "firmware for motion controller board (SL-CONTROLLER)"
HOMEPAGE = "https://gitlab.com/prusa3d/sl1/mc-fw"
SECTION = "firmware"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI = "git://git@gitlab.com/prusa3d/sl1/mc-fw.git;protocol=ssh;branch=master"
SRCREV:pn-${PN} = "720aab85a0745513fa816d3ae77c80cded913db9"
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

