FILESEXTRAPATHS_prepend := "${THISDIR}/avahi:"

SRC_URI += "file://samba.service"
 
do_install_append () {
	install -d ${D}${sysconfdir}/avahi/services
	install ${WORKDIR}/samba.service ${D}${sysconfdir}/avahi/services/samba.service
}
