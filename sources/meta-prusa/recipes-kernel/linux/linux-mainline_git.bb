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
KBRANCH ?= "linux-5.15.y"

DEFAULT_PREFERENCE = "1"

PV = "v5.15.5"
SRCREV:pn-${PN} = "${PV}"
LINUX_VERSION = "${PV}"

SRC_URI="\
	git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;branch=${KBRANCH} \
	file://0001-drm-panel-Add-Ilitek-ILI9806e-panel-driver.patch \
	file://0002-2K-display-over-TC358870.patch \
	file://0003-drm-sun4i-decouple-TCON_DCLK_DIV-value-from-pll_mipi.patch \
	file://0004-tc358870-added-DSI-init-sequence-for-RV059FBB.patch \
	file://0005-tc358870-read-panel-type-from-device-tree.patch \
	file://0101-prusa64-sl1-work-around-mistakenly-written-eFUSEs.patch \
	file://0201-Ethernet-reconnection-fix.patch \
	file://0301-sunxi-Add-misc-EPROBE_DEFER-checks-to-avoid-misleadi.patch \
	file://0401-pwm-sun4i-Avoid-waiting-until-the-next-period.patch \
	file://0501-spi-sun6i-always-set-parent-to-200-MHz.patch \
	file://0601-bus-sunxi-rsb-Fix-shutdown.patch \
	file://1001-dts-create-sun50i-a64-prusa64-sl1-dts.patch \
	file://1002-dts-prusa64-sl1-give-powerpanic-GPIO-line-a-name.patch \
	file://1003-arm64-dts-Add-timer-erratum-property-for-Allwinner-A.patch \
	file://1004-prusa-boot-logo.patch \
	file://1005-a64-increase-mali-frequency.patch \
	file://1006-dts-prusa64-sl1-add-reserved-memory-node-for-lima.patch \
	file://1007-dts-prusa64-sl1-add-SPI-node.patch \
	file://defconfig \
"

S="${WORKDIR}/git"

FILES:${KERNEL_PACKAGE_NAME}-devicetree:append = " /${KERNEL_IMAGEDEST}/**/*.dtb"

FW_DIR = "${RECIPE_SYSROOT}${nonarch_base_libdir}/firmware"

do_configure[depends] += "sla-edid:do_populate_sysroot"

do_configure:prepend() {
	if [ "${@bb.utils.filter('DISTRO_FEATURES', 'ld-is-gold', d)}" ]; then
		sed -i 's/$(CROSS_COMPILE)ld$/$(CROSS_COMPILE)ld.bfd/g' ${S}/Makefile
	fi
	extra_fw=`find ${FW_DIR} -type f | sed "s#^${FW_DIR}/##" | tr '\n' ' '`
	echo "CONFIG_EXTRA_FIRMWARE=\"${extra_fw}\"" >> ${WORKDIR}/defconfig
	echo "CONFIG_EXTRA_FIRMWARE_DIR=\"${FW_DIR}\"" >> ${WORKDIR}/defconfig
}

do_install:append() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -d ${D}/boot

	for dtbf in ${KERNEL_DEVICETREE}; do
		dtb=`normalize_dtb "$dtbf"`
		install -d -m 0755 ${D}/${KERNEL_IMAGEDEST}/`dirname ${dtb}`
		install -m 0644 ${B}/arch/${ARCH}/boot/dts/${dtb} ${D}/${KERNEL_IMAGEDEST}/${dtb}
	done

	rm ${D}/${KERNEL_IMAGEDEST}/vmlinux-${KERNEL_VERSION}
}
