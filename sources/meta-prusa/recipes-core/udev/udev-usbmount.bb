DESCRIPTION = "udev rule & auxilliary utilities to enable USB automount"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"
LICENSE = "GPLv3"

DEPENDS = "systemd"

inherit meson

SRC_URI = " \
	file://src/usbmount.c \
	file://src/usbremount.cpp \
	file://src/meson.build \
	file://automount.rules \
	file://rmdir-on-exit@.service \
"

FILES:${PN} += " \
	${systemd_system_unitdir}/rmdir-on-exit@.service \
	${nonarch_base_libdir}/udev/rules.d/automount.rules \
"

S = "${WORKDIR}/src"

do_install:append() {
	install -d ${D}${nonarch_base_libdir}/udev/rules.d
	install --mode 644 ${WORKDIR}/automount.rules ${D}${nonarch_base_libdir}/udev/rules.d/
	install -d ${D}${systemd_system_unitdir}
	install --mode 644 ${WORKDIR}/rmdir-on-exit@.service ${D}${systemd_system_unitdir}/
}
