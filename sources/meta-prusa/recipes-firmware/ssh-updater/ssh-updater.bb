DESCRIPTION="SSH Public Key updater"

LICENSE = "CLOSED"

SRC_URI = "\
    file://ssh-updater.service \
    https://sl1.prusa3d.com/authorized_keys \
    file://ssh-updater.timer \
"

FILES:${PN} = "\
    /home/root/.ssh/authorized_keys \
"

inherit systemd

BB_STRICT_CHECKSUM = "0"

RDEPENDS:${PN} += "bash"
SSH_DESTINATION = "/home/root/.ssh"

do_install:append () {
    
    # Copy authorized_keys into destinatiion
    install --mode 700 -d ${D}${SSH_DESTINATION}
    install --mode 400 ${DL_DIR}/authorized_keys ${D}${SSH_DESTINATION}/authorized_keys

    # Updater service component
    install -d ${D}${systemd_system_unitdir}/
    install --mode 644 ${WORKDIR}/ssh-updater.service ${D}${systemd_system_unitdir}/

    # Timer service
    install -d ${D}${systemd_system_unitdir}/
    install --mode 644 ${WORKDIR}/ssh-updater.timer ${D}${systemd_system_unitdir}/
}

SYSTEMD_SERVICE:${PN} = "ssh-updater.service ssh-updater.timer"