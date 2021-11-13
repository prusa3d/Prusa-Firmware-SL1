DESCRIPTION = "A udev rule providing stable symlink to the UV meter's emulated USB serial port"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SRC_URI = "file://10-uvmeter.rules"

FILES:${PN} = "${nonarch_base_libdir}/udev/rules.d"

do_install () {
        install -d ${D}${nonarch_base_libdir}/udev/rules.d/
        install --mode 644 ${WORKDIR}/10-uvmeter.rules ${D}${nonarch_base_libdir}/udev/rules.d/10-uvmeter.rules
}
