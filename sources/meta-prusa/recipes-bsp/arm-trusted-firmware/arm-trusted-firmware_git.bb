DESCRIPTION = "Arm Trusted Firmware (ATF)"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

inherit deploy

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = " \
	git://github.com/ARM-software/arm-trusted-firmware.git;branch=master \
	file://0001-plat-allwinner-set-ACIN-current-limit-to-3.5-A.patch \
	file://0002-axp803-add-aldo1-dcdc4-and-eldo2-regulators.patch \
"
SRCREV = "v2.6"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

COMPATIBLE_MACHINE = "(sun50i)"

PLATFORM:sun50i = "sun50i_a64"

CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

do_configure[noexec] = "1"

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

FILES:${PN} = "/boot"
SYSROOT_DIRS += "/boot"
