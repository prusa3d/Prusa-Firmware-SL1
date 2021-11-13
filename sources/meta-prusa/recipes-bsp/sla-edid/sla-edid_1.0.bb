LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

inherit allarch

B = "${WORKDIR}/build"

SRC_URI = " \
	file://edid.h \
	file://dxq608.S \
	file://ls055r1sx04.S \
	file://rv059fbb.S \
	file://checksum.cpp \
"

FILES:${PN} = "${base_libdir}/firmware/edid/*.bin"

do_compile() {
	mkdir -p ${B}
	${BUILD_CXX} -o ${B}/checksum ${WORKDIR}/checksum.cpp
	for edid in dxq608 ls055r1sx04 rv059fbb
	do
		${BUILD_CC} -c -o ${B}/${edid}.o ${WORKDIR}/${edid}.S
		${OBJCOPY} -j .data -Obinary ${B}/${edid}.o ${B}/${edid}.bin
		${B}/checksum ${B}/${edid}.bin
	done
}

do_install() {
        install -d ${D}${base_libdir}/firmware/edid
        install -m 644 ${B}/dxq608.bin			${D}${base_libdir}/firmware/edid/
        install -m 644 ${B}/ls055r1sx04.bin		${D}${base_libdir}/firmware/edid/
        install -m 644 ${B}/rv059fbb.bin		${D}${base_libdir}/firmware/edid/
}
