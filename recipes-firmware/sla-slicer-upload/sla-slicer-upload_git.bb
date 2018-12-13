LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/martin.kopecky/octo-api-upload-service.git;protocol=ssh\
	file://sla-slicer-upload.service \
"
SRCREV = "896c38481bc4e4fb76d989ac7c6cb4e0d1e33704"

inherit systemd

DEPENDS += "python"

S="${WORKDIR}/git"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-slicer-upload.service ${D}${systemd_system_unitdir}/
	install --mode 755 ${S}/octo-uploadd.py ${D}${bindir}
	echo 
	echo
	echo ${S}/octo-uploadd.py ${D}${bindir}
	echo
	echo
}

SYSTEMD_SERVICE_${PN} = "sla-slicer-upload.service"

