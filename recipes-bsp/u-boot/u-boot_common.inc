DESCRIPTION="Upstream's U-boot configured for sunxi devices"
require recipes-bsp/u-boot/u-boot.inc

DEPENDS += " bc-native bison-native dtc-native flex-native swig-native python3-native "
DEPENDS_append_sun50i = " atf-sunxi "

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

SRC_URI_append = " file://fw_env.config "

DEFAULT_PREFERENCE_sun50i="1"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += ' HOSTLDSHARED="${BUILD_CC} -shared ${BUILD_LDFLAGS} ${BUILD_CFLAGS}" '
EXTRA_OEMAKE_append_sun50i = " BL31=${DEPLOY_DIR_IMAGE}/bl31.bin "

SPL_BINARY = "spl/sunxi-spl.bin"

do_compile_sun50i[depends] += "atf-sunxi:do_deploy"

PACKAGES_prepend = "${PN}-fw-utils "
FILES_${PN}-fw-utils = "		\
	${base_sbindir}/fw_printenv	\
	${base_sbindir}/fw_setenv	\
	${sysconfdir}/fw_env.config	\
"

FW_UTILS_EXTRA_MAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${CC} ${CFLAGS} ${LDFLAGS}" HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}" V=1'

do_compile_append() {
	bbnote ${MAKE} ${FW_UTILS_EXTRA_MAKE} envtools
	${MAKE} ${FW_UTILS_EXTRA_MAKE} envtools
}

do_install_append () {
        install -d ${D}${base_sbindir}
        install -d ${D}${sysconfdir}
        install -m 755 ${B}/tools/env/fw_printenv ${D}${base_sbindir}/fw_printenv
        install -m 755 ${B}/tools/env/fw_printenv ${D}${base_sbindir}/fw_setenv
        install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}
