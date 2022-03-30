DESCRIPTION="Emergency Rauc updater service"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI = "\
	file://emergencyupdate.service \
"

inherit systemd

RDEPENDS:${PN} += "bash coreutils rauc"

FILES:${PN} += "\
	${libdir}/systemd/system/multi-user.target.wants/emergencyupdate.service \
"

do_install:append () {
	# Emergency update service
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/emergencyupdate.service ${D}${systemd_system_unitdir}/
	
	# Enable service
	install -d ${D}${libdir}/systemd/system/multi-user.target.wants
	ln -s ${libdir}/systemd/system/emergencyupdate.service ${D}${libdir}/systemd/system/multi-user.target.wants/emergencyupdate.service
}

SYSTEMD_SERVICE:${PN} = "emergencyupdate.service"
 
