LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/martin.kopecky/octo-api-upload-service.git;protocol=ssh\
	file://sla-slicer-upload.service \
	file://avahi/octoprint.service \
"
SRCREV = "28a4c8deb896a520f58f54420dd9dab7b9627364"

inherit systemd setuptools

DEPENDS += "python python-setuptools"
RDEPENDS_${PN} += "avahi-daemon"

S="${WORKDIR}/git"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-slicer-upload.service ${D}${systemd_system_unitdir}/
#	install -d ${D}${bindir}
#	#install --mode 755 ${S}/octo-uploadd.py ${D}${bindir}
#	oe_runmake DESTDIR=${D}${bindir} install

	# Avahi service definition
	install -d ${D}${sysconfdir}/avahi/services
	install --mode 644 ${WORKDIR}/avahi/octoprint.service ${D}${sysconfdir}/avahi/services/octoprint.service
}

SYSTEMD_SERVICE_${PN} = "sla-slicer-upload.service"

