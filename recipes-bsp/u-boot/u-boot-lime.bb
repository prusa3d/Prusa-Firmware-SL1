DESCRIPTION = "U-Boot port for sunxi"

require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

COMPATIBLE_MACHINE = "lime-a64-sunxi"
DEFAULT_PREFERENCE_lime-a64-sunxi = "1"

UBOOT_ENV_SUFFIX = "env"
UBOOT_ENV = "uboot"

SPL_BINARY="u-boot-sunxi-with-spl.bin"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "\
	git://github.com/A64-TERES/u-boot_new.git;protocol=git \
	file://uboot.env \
	file://sunxi-spl.bin \
	file://u-boot.itb \
	"

PE = "1"
PV = "v2014.07+git${SRCPV}"
SRCREV = "f38a9d42a764cb9c1afa31ea0bc9757bbff901e4"
S = "${WORKDIR}/git"

do_compile () {
	install -d ${B}
	install -m 644 ${WORKDIR}/sunxi-spl.bin ${B}/${SPL_BINARY}
	install -m 644 ${WORKDIR}/u-boot.itb ${B}/${UBOOT_BINARY}
	install -m 644 ${WORKDIR}/uboot.env ${B}/${UBOOT_ENV_BINARY}
}

#do_install () {
#	install -d ${D}/boot
#	install -m 644 ${WORKDIR}/u-boot.itb ${D}/boot/u-boot/u-boot-${UBOOT_IMAGE}
#	ln -sf ${UBOOT_IMAGE} ${D}/boot/${UBOOT_BINARY}
#	install -m 644 ${B}/${SPL_BINARY} ${D}/boot/${SPL_IMAGE}
#	ln -sf ${SPL_IMAGE} ${D}/boot/${SPL_BINARYNAME}
