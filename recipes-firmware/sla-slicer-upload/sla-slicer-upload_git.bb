LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/martin.kopecky/octo-api-upload-service.git;protocol=ssh\
	file://sla-slicer-upload.service \
	file://sla-slicer-upload-restarter.service \
	file://sla-slicer-upload-restarter.path \
	file://avahi/octoprint.service \
	file://nginx/octoprint \
"
SRCREV = "1f8b6260bc85db9bd0f72cdb9f8af0b26a490d36"

inherit systemd setuptools

DEPENDS += "python python-setuptools"
RDEPENDS_${PN} += "avahi-daemon avahi-restarter api-keygen"

S="${WORKDIR}/git"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-slicer-upload.service ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-slicer-upload-restarter.service ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-slicer-upload-restarter.path ${D}${systemd_system_unitdir}/

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
	ln -s ${systemd_system_unitdir}/sla-slicer-upload-restarter.path ${D}${systemd_system_unitdir}/multi-user.target.wants/

	# Remove ununsed dir
	rmdir ${D}/usr/share
}

SYSTEMD_AUTO_ENABLE = "disable"
SYSTEMD_SERVICE_${PN} = "sla-slicer-upload.service sla-slicer-upload-restarter.service sla-slicer-upload-restarter.path"
