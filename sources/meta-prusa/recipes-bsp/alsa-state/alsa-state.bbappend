FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
    # Install copy of default mixer states as factory defaults
    install -d ${D}/usr/share/factory/var/lib/alsa
    install -m 0644 ${WORKDIR}/*.state ${D}/usr/share/factory/var/lib/alsa
}

FILES_alsa-states += "/usr/share/factory/var/lib/alsa/*.state"

