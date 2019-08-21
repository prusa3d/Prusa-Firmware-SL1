LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

inherit allarch

B = "${WORKDIR}/build"

SRC_URI = " \
	file://ls055r1sx04_148.5mhz.S \
	file://checksum.c \
"

FILES_${PN} = "${base_libdir}/firmware/edid/ls055r1sx04_148.5mhz.bin"

do_compile() {
	mkdir -p ${B}
	EDID="ls055r1sx04_148.5mhz"
	${BUILD_CC} -o ${B}/checksum ${WORKDIR}/checksum.c
	${BUILD_CC} -c -DCHKSUM="0x00" -o ${B}/${EDID}.nosum.o ${WORKDIR}/${EDID}.S
	${OBJCOPY} -Obinary ${B}/${EDID}.nosum.o ${B}/${EDID}.nosum.bin
	CHKSUM=$(${B}/checksum < ${B}/${EDID}.nosum.bin)
	${BUILD_CC} -c -DCHKSUM="${CHKSUM}" -o ${B}/${EDID}.o ${WORKDIR}/${EDID}.S
	${OBJCOPY} -Obinary ${B}/${EDID}.o ${B}/${EDID}.bin
}

do_install() {
        install -d ${D}${base_libdir}/firmware/edid
        install -m 644 ${B}/ls055r1sx04_148.5mhz.bin	${D}${base_libdir}/firmware/edid/
}
