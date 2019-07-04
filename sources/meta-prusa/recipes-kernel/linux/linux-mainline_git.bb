SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
COMPATIBLE_MACHINE = "sun50i"
INC_PR = "r0"

inherit kernel siteinfo

LOCALVERSION ?= ""

# Since we're not using git, this doesn't make a difference, but we need to fill
# in something or kernel-yocto.bbclass will fail.
KBRANCH ?= "master"

DEFAULT_PREFERENCE = "1"

SRCREV_pn-${PN} = "e2fb0d1bfa66298934766f33371da2b5bec4c2bf"
PV = "v4.18.0-rc6+git${SRCPV}"

SRC_URI="\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/linux.git;protocol=ssh;branch=${KBRANCH} \
	file://defconfig \
	file://freescale_timer_fix.patch \
"

S="${WORKDIR}/git"

FILES_${KERNEL_PACKAGE_NAME}-devicetree_append = " /${KERNEL_IMAGEDEST}/**/*.dtb"

do_install_append() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -d ${D}/boot

	for dtbf in ${KERNEL_DEVICETREE}; do
		dtb=`normalize_dtb "$dtbf"`
		install -d -m 0755 ${D}/${KERNEL_IMAGEDEST}/`dirname ${dtb}`
		install -m 0644 ${B}/arch/${ARCH}/boot/dts/${dtb} ${D}/${KERNEL_IMAGEDEST}/${dtb}
	done

	for imageType in ${KERNEL_IMAGETYPES} ; do
		rm ${D}/${KERNEL_IMAGEDEST}/${imageType}-${KERNEL_VERSION}
		install -m 0644 ${KERNEL_OUTPUT_DIR}/${imageType}.initramfs ${D}/${KERNEL_IMAGEDEST}/${imageType}-${KERNEL_VERSION}
	done
	rm ${D}/${KERNEL_IMAGEDEST}/vmlinux-${KERNEL_VERSION}
}


deltask bundle_initramfs 
addtask bundle_initramfs after do_compile_kernelmodules before do_install
