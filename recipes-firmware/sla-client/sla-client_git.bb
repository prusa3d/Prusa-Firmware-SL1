LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/martin.kopecky/new-sla-client-application.git;protocol=ssh \
	file://sla-client.service \
	file://install-path.patch \
"
SRCREV = "ade0f7dd45ee6656c7d3daca8b07dc7ae527fc73"

inherit qmake5 systemd

DEPENDS += "qtbase qtquickcontrols qtquickcontrols2 qtwebsockets qtsvg"

S="${WORKDIR}/git"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install ${WORKDIR}/sla-client.service ${D}${systemd_system_unitdir}/
}

SYSTEMD_SERVICE_${PN} = "sla-client.service"
