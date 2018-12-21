LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/martin.kopecky/octo-api-upload-service.git;protocol=ssh\
	file://sla-slicer-upload.service \
	file://sla-slicer-upload-keygen.service \
	file://avahi/octoprint.service \
	file://use_generated_key.patch \
"
SRCREV = "28a4c8deb896a520f58f54420dd9dab7b9627364"

inherit systemd setuptools

DEPENDS += "python python-setuptools"
RDEPENDS_${PN} += "avahi-daemon"

FILES_${PN} += "\
	${sysconfdir}/sla \
"

S="${WORKDIR}/git"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-slicer-upload.service ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-slicer-upload-keygen.service ${D}${systemd_system_unitdir}/
#	install -d ${D}${bindir}
#	#install --mode 755 ${S}/octo-uploadd.py ${D}${bindir}
#	oe_runmake DESTDIR=${D}${bindir} install

	# Avahi service definition
	install -d ${D}${sysconfdir}/avahi/services
	install --mode 644 ${WORKDIR}/avahi/octoprint.service ${D}${sysconfdir}/avahi/services/octoprint.service
	
	# Ensure key dir exists
	install -d ${D}${sysconfdir}/sla
	
	# Remove ununsed dir
	rmdir ${D}/usr/share
}

SYSTEMD_SERVICE_${PN} = "sla-slicer-upload.service sla-slicer-upload-keygen.service"

