SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
COMPATIBLE_MACHINE = "sun50i"
INC_PR = "r0"

inherit kernel siteinfo

LOCALVERSION ?= ""

# Since we're not using git, this doesn't make a difference, but we need to fill
# in something or kernel-yocto.bbclass will fail.
KBRANCH ?= "linux-5.6.y"

DEFAULT_PREFERENCE = "1"

SRCREV_pn-${PN} = "v5.6.2"
PV = "v5.6.2"
LINUX_VERSION = "${PV}"

SRC_URI="\
	git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;branch=${KBRANCH} \
"
SRC_URI_append_sun50i = " \
	file://0001-drm-panel-Add-TDO-TL040WV27CT3-support.patch \
	file://0002-2K-display-over-TC358870.patch \
	file://0003-drm-sun4i-decouple-TCON_DCLK_DIV-value-from-pll_mipi.patch \
	file://0101-prusa64-sl1-work-around-mistakenly-written-eFUSEs.patch \
	file://0201-Ethernet-reconnection-fix.patch \
	file://0301-sunxi-Add-misc-EPROBE_DEFER-checks-to-avoid-misleadi.patch \
	file://1001-dts-create-sun50i-a64-prusa64-sl1-dts.patch \
	file://1002-dts-prusa64-sl1-give-powerpanic-GPIO-line-a-name.patch \
	file://1003-arm64-dts-Add-timer-erratum-property-for-Allwinner-A.patch \
	file://defconfig \
"

S="${WORKDIR}/git"

FILES_${KERNEL_PACKAGE_NAME}-devicetree_append = " /${KERNEL_IMAGEDEST}/**/*.dtb"

do_configure_prepend() {
	if [ "${@bb.utils.filter('DISTRO_FEATURES', 'ld-is-gold', d)}" ]; then
		sed -i 's/$(CROSS_COMPILE)ld$/$(CROSS_COMPILE)ld.bfd/g' ${S}/Makefile
	fi
}

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
