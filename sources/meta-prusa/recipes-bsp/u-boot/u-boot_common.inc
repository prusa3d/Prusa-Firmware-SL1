DESCRIPTION="Upstream's U-boot configured for sunxi devices"

inherit pythonnative
require recipes-bsp/u-boot/u-boot.inc

DEPENDS += " bc-native bison-native dtc-native flex-native swig-native grep-native sed-native u-boot-fw-utils-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

SRC_URI_append = " file://fw_env.config "

DEFAULT_PREFERENCE_sun50i="1"

PACKAGECONFIG[atf] = "BL31=${STAGING_DIR_HOST}/boot/bl31.bin,,arm-trusted-firmware"
PACKAGECONFIG_append_sun50i = " atf"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += ' HOSTLDSHARED="${BUILD_CC} -shared ${BUILD_LDFLAGS} ${BUILD_CFLAGS}"'
EXTRA_OEMAKE += " ${PACKAGECONFIG_CONFARGS}"

SPL_BINARY = "spl/sunxi-spl.bin"

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

	${READELF}  -p .rodata.default_environment ${B}/env/common.o	\
		| grep '\[[ 0-9a-f]\{6\}\]'				\
		| sed -e 's/^  \[[ 0-9a-f]\{6\}\]  //'			\
		| sed -e 's/^\([_0-9a-zA-Z]\+\)=\(.\+\)$/\1 \2/'	\
		> ${B}/setenv.scr
	echo "${B}/uboot.env 0x0 0x20000" > ${B}/setenv.config
	dd if=/dev/zero of=${B}/uboot.env bs=512k count=1
	fw_setenv --config ${B}/setenv.config --script ${B}/setenv.scr --lock ${B}
}

do_install_append () {
        install -d ${D}${base_sbindir}
        install -d ${D}${sysconfdir}
        install -m 755 ${B}/tools/env/fw_printenv ${D}${base_sbindir}/fw_printenv
        install -m 755 ${B}/tools/env/fw_printenv ${D}${base_sbindir}/fw_setenv
        install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}


UBOOT_ENV_BINARY = "uboot.env"
UBOOT_ENV_IMAGE = "uboot-${MACHINE}-${PV}-${PR}.env"
UBOOT_ENV_SYMLINK = "uboot-${MACHINE}.env"

SETENV = "setenv.scr"
SETENV_IMAGE = "setenv-${MACHINE}-${PV}-${PR}.scr"
SETENV_SYMLINK = "setenv-${MACHINE}.scr"

do_deploy_append () {
	install -m 644 ${B}/${UBOOT_ENV_BINARY} ${DEPLOYDIR}/${UBOOT_ENV_IMAGE}
	rm -f ${DEPLOYDIR}/${UBOOT_ENV_BINARY} ${DEPLOYDIR}/${UBOOT_ENV_SYMLINK}
	ln -sf ${UBOOT_ENV_IMAGE} ${DEPLOYDIR}/${UBOOT_ENV_BINARY}
	ln -sf ${UBOOT_ENV_IMAGE} ${DEPLOYDIR}/${UBOOT_ENV_SYMLINK}

	install -m 644 ${B}/${SETENV} ${DEPLOYDIR}/${SETENV_IMAGE}
	rm -f ${DEPLOYDIR}/${SETENV} ${DEPLOYDIR}/${SETENV_SYMLINK}
	ln -sf ${SETENV_IMAGE} ${DEPLOYDIR}/${SETENV}
	ln -sf ${SETENV_IMAGE} ${DEPLOYDIR}/${SETENV_SYMLINK}
}

