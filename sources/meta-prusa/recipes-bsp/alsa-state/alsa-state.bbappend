FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = "\
        file://alsa-factory-restore.service \
"

FILES_${PN} += " \
	${systemd_system_unitdir}/alsa-factory-restore.service \
	${systemd_system_unitdir}/sound.target.wants/alsa-factory-restore.service \
"

do_install_append() {
    # Install copy of default mixer states as factory defaults
    install -d ${D}/usr/share/factory/var/lib/alsa
    install -m 0644 ${WORKDIR}/*.state ${D}/usr/share/factory/var/lib/alsa

    # Install factory sound restore service
    install -d ${D}${systemd_system_unitdir}
    install --mode 644 ${WORKDIR}/alsa-factory-restore.service ${D}${systemd_system_unitdir}/alsa-factory-restore.service

    # Enable factory sound restore service
    install -d ${D}${libdir}/systemd/system/sound.target.wants
    ln -s ${systemd_system_unitdir}/alsa-factory-restore.service ${D}${systemd_system_unitdir}/sound.target.wants/alsa-factory-restore.service
}

FILES_alsa-states += "/usr/share/factory/var/lib/alsa/*.state"

