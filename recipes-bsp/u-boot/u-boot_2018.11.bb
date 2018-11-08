DESCRIPTION="Upstream's U-boot configured for sunxi devices"

require recipes-bsp/u-boot/u-boot.inc

DEPENDS += " bc-native bison-native dtc-native flex-native swig-native python3-native "
DEPENDS_append_sun50i = " atf-sunxi "

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

COMPATIBLE_MACHINE = "(sun4i|sun5i|sun7i|sun8i|sun50i)"

DEFAULT_PREFERENCE_sun4i="1"
DEFAULT_PREFERENCE_sun5i="1"
DEFAULT_PREFERENCE_sun7i="1"
DEFAULT_PREFERENCE_sun8i="1"
DEFAULT_PREFERENCE_sun50i="1"

SRC_URI = "git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/u-boot.git;protocol=ssh;branch=master \
           file://boot.cmd \
           "
SRCREV = "68985e4fc3311bfee4266188fd4c4f011d5609d2"

PV = "v2018.11-rc1+git${SRCPV}"
PE = "2"

S = "${WORKDIR}/git"

UBOOT_ENV_SUFFIX = "scr"
UBOOT_ENV = "boot"

EXTRA_OEMAKE += ' HOSTLDSHARED="${BUILD_CC} -shared ${BUILD_LDFLAGS} ${BUILD_CFLAGS}" '
EXTRA_OEMAKE_append_sun50i = " BL31=${IMGDEPLOYDIR}/bl31.bin "

do_compile_sun50i[depends] += "atf-sunxi:do_deploy"

do_compile_append() {
    ${B}/tools/mkimage -C none -A arm -T script -d ${WORKDIR}/boot.cmd ${WORKDIR}/${UBOOT_ENV_BINARY}
}
