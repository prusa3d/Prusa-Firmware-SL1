LICENSE = "CLOSED"

SRC_URI = "\
	file://emergencyupdate.service \
"

inherit systemd

RDEPENDS_${PN} += "bash coreutils rauc"

FILES_${PN} += "\
	${libdir}/systemd/system/multi-user.target.wants/emergencyupdate.service \
"

do_install_append () {
	# Emergency update service
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/emergencyupdate.service ${D}${systemd_system_unitdir}/
	
	# Enable service
	install -d ${D}${libdir}/systemd/system/multi-user.target.wants
	ln -s ${libdir}/systemd/system/emergencyupdate.service ${D}${libdir}/systemd/system/multi-user.target.wants/emergencyupdate.service
}

SYSTEMD_SERVICE_${PN} = "emergencyupdate.service"
 
