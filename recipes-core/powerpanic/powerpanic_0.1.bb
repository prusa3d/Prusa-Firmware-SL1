DESCRIPTION = "Power panic response service"
LICENSE = "CLOSED"
DEPENDS = "libgpiod"

inherit systemd

SRC_URI = "\
	file://panic.cpp \
	file://panic.service \
"

FILES_${PN} = "\
  ${bindir}/panic \
"

do_compile() {
	${CXX} ${CXXFLAGS} ${LDFLAGS} ${WORKDIR}/panic.cpp -o panic -lgpiod
}

do_install() {
	# Panic
	install -d ${D}${bindir}
	install panic ${D}${bindir}/panic

	# Service
	install -d ${D}${systemd_system_unitdir}
	install ${WORKDIR}/panic.service ${D}${systemd_system_unitdir}/panic.service
}

SYSTEMD_SERVICE_${PN} = "panic.service"
