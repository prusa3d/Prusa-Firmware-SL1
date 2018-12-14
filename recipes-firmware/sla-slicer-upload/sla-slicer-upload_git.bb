LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/martin.kopecky/octo-api-upload-service.git;protocol=ssh\
	file://sla-slicer-upload.service \
"
SRCREV = "f679800f6e220ab4b6a92ae7df0b90f4ba517170"

inherit systemd setuptools

DEPENDS += "python python-setuptools"

S="${WORKDIR}/git"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-slicer-upload.service ${D}${systemd_system_unitdir}/
#	install -d ${D}${bindir}
#	#install --mode 755 ${S}/octo-uploadd.py ${D}${bindir}
#	oe_runmake DESTDIR=${D}${bindir} install
}

SYSTEMD_SERVICE_${PN} = "sla-slicer-upload.service"

