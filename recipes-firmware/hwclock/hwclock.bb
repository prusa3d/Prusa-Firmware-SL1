SUMMARY = "hw clock service"
DESCRIPTION = "Systemd service syncs time to RTC at poweroff"
LICENSE = "CLOSED"

SRC_URI =  " \
    file://hwclock.service \
"

do_compile () {
}

do_install () {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/hwclock.service ${D}${systemd_unitdir}/system
}

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "hwclock.service"

inherit allarch systemd
