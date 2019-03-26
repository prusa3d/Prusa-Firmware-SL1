LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/martin.kopecky/octo-api-upload-service.git;protocol=ssh\
	file://sla-slicer-upload.service \
	file://sla-slicer-upload-restarter.service \
	file://sla-slicer-upload-restarter.path \
	file://avahi/octoprint.service \
	file://use_generated_key.patch \
	file://listen-localhost.patch \
	file://nginx/octoprint \
"
SRCREV = "28a4c8deb896a520f58f54420dd9dab7b9627364"

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
	
	# Remove ununsed dir
	rmdir ${D}/usr/share
}

SYSTEMD_SERVICE_${PN} = "sla-slicer-upload.service sla-slicer-upload-restarter.path"
