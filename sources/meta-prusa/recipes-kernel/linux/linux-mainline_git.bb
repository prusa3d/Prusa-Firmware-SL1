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
KBRANCH ?= "linux-5.4.y"

DEFAULT_PREFERENCE = "1"

SRCREV_pn-${PN} = "v5.4.6"
PV = "v5.4.6"
LINUX_VERSION = "${PV}"

SRC_URI="git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;branch=${KBRANCH}"
SRC_URI_append_sun50i = " \
	file://0001-drm-sun4i-dsi-Fixes-updates-A33-reworked.patch \
	file://0002-drm-sun4i-Allwinner-A64-MIPI-DSI-support.patch \
	file://0003-drm-sun4i-dsi-misc-fixes.patch \
	file://0004-drm-added-panel-driver-for-a-FullHD-5-LCD-SHARP-LS05.patch \
	file://0005-drm-panel-ilitek-ili9806e-WIP-implementation.patch \
	file://0006-sun4i-drm-assume-HDMI-is-always-connected.patch \
	file://0007-tc358870-hdmi-dsi-bridge-initial-commit.patch \
	file://0008-arm64-dts-allwinner-set-GPU-clock-to-432-MHz.patch \
	file://0009-480p-and-2k-displays-working-together.patch \
	file://0101-add-thermal-sensor-driver-for-A64-A83T-H3-H5-H6-R40.patch \
	file://0201-net-stmmac-enable-MAC-address-to-be-read-from-a-nvme.patch \
	file://0301-sunxi-Add-misc-EPROBE_DEFER-checks-to-avoid-misleadi.patch \
	file://1001-dts-copy-sun50i-a64-olinuxino.dts-into-sun50i-a64-pr.patch \
	file://1002-dts-prusa64-sl1-fix-DAPM-widgets.patch \
	file://1003-freescale-timer-fix.patch \
	file://1004-dts-prusa64-sl1-enable-ac_power_supply-node.patch \
	file://1005-prusa64-sl1-work-around-mistakenly-written-eFUSEs.patch \
	file://1006-dts-prusa64-sl1-give-powerpanic-GPIO-line-a-name.patch \
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
