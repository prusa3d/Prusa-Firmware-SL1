DESCRIPTION = "Power panic response service"
LICENSE = "CLOSED"
DEPENDS = "libgpiod"

inherit systemd

SRC_URI = "\
	git://git@gitlab.com/prusa3d/sl1/powerpanic.git;protocol=ssh;branch=master \
"
SRCREV = "f757156dd4789ff5d21af70b2acea3cb886e9abf"

FILES_${PN} = "\
  ${bindir}/panic \
"

S = "${WORKDIR}/git"

do_install() {
	# Panic
	install -d ${D}${bindir}
	install ${S}/panic ${D}${bindir}/panic

	# Service
	install -d ${D}${systemd_system_unitdir}
	install --mode 644 ${S}/systemd/panic.service ${D}${systemd_system_unitdir}/panic.service
}

SYSTEMD_SERVICE_${PN} = "panic.service"
