DESCRIPTION="Emergency Rauc updater service"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "\
	file://emergencyupdate.service \
	file://GPLv3.patch \
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
 
