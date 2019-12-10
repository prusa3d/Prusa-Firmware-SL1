DESCRIPTION = "Arm Trusted Firmware (ATF)"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=c709b197e22b81ede21109dbffd5f363"

inherit deploy

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = " \
	git://github.com/ARM-software/arm-trusted-firmware.git;branch=master \
	file://0002-drivevbus.patch \
	file://0003-aldo1-regulator.patch \
	file://0004-set-current-limit.patch \
"
SRCREV = "9a25f98261c134e3af4c1610c4afc74b01201fa2"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

COMPATIBLE_MACHINE = "(sun50i)"

PLATFORM_sun50i = "sun50i_a64"

CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

do_configure[noexec] = "1"
#do_install[noexec] = "1"

EXTRA_OEMAKE = 'BUILD_BASE=${B} LD=${TARGET_PREFIX}ld.bfd CROSS_COMPILE=${TARGET_PREFIX} PLAT=${PLATFORM}'

do_compile() {
	oe_runmake -C ${S} all
}

do_install() {
	install -d ${D}/boot
	install -m 0644 ${B}/${PLATFORM}/release/bl31.bin ${D}/boot/
}

do_deploy() {
	install -d ${DEPLOYDIR}
	install -m 0644 ${B}/${PLATFORM}/release/bl31.bin ${DEPLOYDIR}/bl31.bin
}


addtask deploy before do_build after do_compile

FILES_${PN} = "/boot"
SYSROOT_DIRS += "/boot"
