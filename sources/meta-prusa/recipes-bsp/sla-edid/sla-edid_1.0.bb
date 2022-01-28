LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

inherit allarch

B = "${WORKDIR}/build"

SRC_URI = " \
	file://edids \
	file://checksum.cpp \
"

FILES:${PN} = "${base_libdir}/firmware/edid/*.bin"

do_compile() {
	mkdir -p ${B}
	${BUILD_CXX} -o ${B}/checksum ${WORKDIR}/checksum.cpp
	for edid_src in ${WORKDIR}/edids/*.S
	do
		edid_S=${edid_src##*/}
		edid=${edid_S%.S}
		${BUILD_CC} -c -o ${B}/${edid}.o ${edid_src}
		${OBJCOPY} -j .data -Obinary ${B}/${edid}.o ${B}/${edid}.bin
		${B}/checksum ${B}/${edid}.bin
	done
}

do_install() {
        install -d ${D}${base_libdir}/firmware/edid
        install -m 644 ${B}/*.bin			${D}${base_libdir}/firmware/edid/
}
