DESCRIPTION = "Power panic response service"

LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"
LICENSE = "GPLv3"

DEPENDS = "libgpiod"

inherit systemd

SRC_URI = "\
	git://git@gitlab.com/prusa3d/sl1/powerpanic.git;protocol=ssh;branch=master \
"
SRCREV = "9b78df98c9f96a539fa1e97af1764e941f162477"

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
