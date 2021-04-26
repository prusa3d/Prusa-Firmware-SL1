LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

inherit allarch

B = "${WORKDIR}/build"

SRC_URI = " \
	file://edid.h \
	file://dxq608.S \
	file://ls055r1sx04.S \
	file://rv059fbb.S \
	file://checksum.c \
"

FILES_${PN} = "${base_libdir}/firmware/edid/*.bin"

do_compile() {
	mkdir -p ${B}
	${BUILD_CC} -o ${B}/checksum ${WORKDIR}/checksum.c
	for edid in dxq608 ls055r1sx04 rv059fbb
	do
		${BUILD_CC} -c -DCRC="0x00" -o ${B}/${edid}.nosum.o ${WORKDIR}/${edid}.S
		${OBJCOPY} -Obinary ${B}/${edid}.nosum.o ${B}/${edid}.nosum.bin
		crc=$(${B}/checksum < ${B}/${edid}.nosum.bin)
		rm ${B}/${edid}.nosum.bin
		${BUILD_CC} -c -DCRC="${crc}" -o ${B}/${edid}.o ${WORKDIR}/${edid}.S
		${OBJCOPY} -Obinary ${B}/${edid}.o ${B}/${edid}.bin
	done
}

do_install() {
        install -d ${D}${base_libdir}/firmware/edid
        install -m 644 ${B}/dxq608.bin			${D}${base_libdir}/firmware/edid/
        install -m 644 ${B}/ls055r1sx04.bin		${D}${base_libdir}/firmware/edid/
        install -m 644 ${B}/rv059fbb.bin		${D}${base_libdir}/firmware/edid/
}
