LICENSE = "CLOSED"
#asdf: 
SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/martin.kopecky/octo-api-upload-service.git;protocol=ssh\
	file://sla-slicer-upload.service \
	file://avahi/octoprint.service \
	file://nginx/octoprint \
"
SRCREV = "2c9228c2eaae68ea32ba0e96acd842071d0336b2"
inherit systemd setuptools3

RDEPENDS_${PN} += "python3-cherrypy python3-pyinotify python3-json avahi-daemon avahi-restarter api-keygen"

S="${WORKDIR}/git"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-slicer-upload.service ${D}${systemd_system_unitdir}/

	# Avahi service definition
	install -d ${D}${sysconfdir}/avahi/services
	install --mode 644 ${WORKDIR}/avahi/octoprint.service ${D}${sysconfdir}/avahi/services/octoprint.service

	# Nginx site
	install -d ${D}${sysconfdir}/nginx/sites-available
	install ${WORKDIR}/nginx/octoprint ${D}${sysconfdir}/nginx/sites-available/octoprint
	install -d ${D}${sysconfdir}/nginx/sites-enabled
	ln -s ${sysconfdir}/nginx/sites-available/octoprint ${D}${sysconfdir}/nginx/sites-enabled/octoprint

	# Enable services
	install -d ${D}${systemd_system_unitdir}/multi-user.target.wants
	ln -s ${systemd_system_unitdir}/sla-slicer-upload.service ${D}${systemd_system_unitdir}/multi-user.target.wants/

	# Remove ununsed dir
	rmdir ${D}/usr/share
}

SYSTEMD_AUTO_ENABLE = "disable"
SYSTEMD_SERVICE_${PN} = "sla-slicer-upload.service"
