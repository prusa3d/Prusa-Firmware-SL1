LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/martin.kopecky/new-sla-client-application.git;protocol=ssh \
	file://sla-client.service \
	file://install-path.patch \
"
SRCREV = "8f1670fc0b0b2b59327e51c5474d20ff64384bb0"

inherit qmake5 systemd

DEPENDS += "qtbase qtquickcontrols qtquickcontrols2 qtwebsockets qtsvg"

S="${WORKDIR}/git"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install ${WORKDIR}/sla-client.service ${D}${systemd_system_unitdir}/
}

SYSTEMD_SERVICE_${PN} = "sla-client.service"
