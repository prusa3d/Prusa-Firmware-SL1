SRC_URI = "file://qhd.bin"
LICENSE = "CLOSED"
FILES_${PN} = "${base_libdir}/firmware/edid/qhd.bin"

do_install() {
        install -d ${D}${base_libdir}/firmware/edid
        install -m 644 ${WORKDIR}/qhd.bin	${D}${base_libdir}/firmware/edid/qhd.bin
}
