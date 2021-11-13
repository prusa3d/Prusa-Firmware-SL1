DESCRIPTION = "A udev rule setting (e)mmc scheduler to bfq"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SRC_URI = "file://11-io-scheduler.rules"

FILES:${PN} = "${nonarch_base_libdir}/udev/rules.d"

do_install () {
        install -d ${D}${nonarch_base_libdir}/udev/rules.d/
        install --mode 644 ${WORKDIR}/11-io-scheduler.rules ${D}${nonarch_base_libdir}/udev/rules.d/11-io-scheduler.rules
}

