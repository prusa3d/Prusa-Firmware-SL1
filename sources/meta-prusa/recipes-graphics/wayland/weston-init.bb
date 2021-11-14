SUMMARY = "Startup script and systemd unit file for the Weston Wayland compositor"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = " \
	file://weston.ini \
	file://weston.service \
	file://weston.socket \
"

S = "${WORKDIR}"

do_install() {
	install -D -p -m0644 ${WORKDIR}/weston.ini ${D}${sysconfdir}/xdg/weston/weston.ini
	install -D -p -m0644 ${WORKDIR}/weston.service ${D}${systemd_system_unitdir}/weston.service
	install -D -p -m0644 ${WORKDIR}/weston.socket ${D}${systemd_system_unitdir}/weston.socket
	sed -i -e s:/usr/bin:${bindir}:g \
		${D}${systemd_system_unitdir}/weston.service
}

inherit features_check systemd

# rdepends on weston which depends on virtual/egl
REQUIRED_DISTRO_FEATURES = "opengl pam"

RDEPENDS:${PN} = "weston kbd"

FILES:${PN} += "\
    ${sysconfdir}/xdg/weston/weston.ini \
    ${systemd_system_unitdir}/weston.service \
    ${systemd_system_unitdir}/weston.socket \
    "

CONFFILES:${PN} += "${sysconfdir}/xdg/weston/weston.ini"

SYSTEMD_SERVICE:${PN} = "weston.service weston.socket"
