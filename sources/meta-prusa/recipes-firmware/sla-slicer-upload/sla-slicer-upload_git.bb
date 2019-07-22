LICENSE = "GPLv3+"
SRC_URI = "\
	git://git@gitlab.com/prusa3d/sl1/project-upload-service.git;protocol=ssh\
	file://sla-slicer-upload.service \
	file://avahi/octoprint.service \
	file://nginx/octoprint \
"
LIC_FILES_CHKSUM = "\
        file://LICENSE;md5=5b4473596678d62d9d83096273422c8c \
"

SRCREV = "1966699abf0e6d91789544d922bacd430425c981"
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
