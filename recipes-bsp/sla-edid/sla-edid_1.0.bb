SRC_URI = "file://ls055r1sx04_148.5mhz.bin"
LICENSE = "CLOSED"
FILES_${PN} = "${base_libdir}/firmware/edid/ls055r1sx04_148.5mhz.bin"

do_install() {
        install -d ${D}${base_libdir}/firmware/edid
        install -m 644 ${WORKDIR}/ls055r1sx04_148.5mhz.bin	${D}${base_libdir}/firmware/edid/
}
