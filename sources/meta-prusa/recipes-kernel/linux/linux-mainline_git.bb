SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
COMPATIBLE_MACHINE = "sun50i"
INC_PR = "r0"

inherit kernel siteinfo

LOCALVERSION ?= ""
PACKAGE_ARCH = "${SOC_FAMILY}"
KERNEL_DEVICETREE:sun50i = "\
	allwinner/sun50i-a64-prusa64-sl1.dtb \
	allwinner/sun50i-a64-prusa64-sl2.dtb \
"

# Since we're not using git, this doesn't make a difference, but we need to fill
# in something or kernel-yocto.bbclass will fail.
KBRANCH ?= "linux-5.16.y"

DEFAULT_PREFERENCE = "1"

PV = "v5.16.11"
SRCREV:pn-${PN} = "${PV}"
LINUX_VERSION = "${PV}"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;branch=${KBRANCH} \
           file://defconfig \
           file://0001-drm-panel-Add-Ilitek-ILI9806e-panel-driver.patch \
           file://0002-drm-sun4i-decouple-TCON_DCLK_DIV-value-from-pll_mipi.patch \
           file://0003-tc358870-hdmi-dsi-bridge-initial-commit.patch \
           file://0004-tc358870-added-DSI-init-sequence-for-RV059FBB.patch \
           file://0101-prusa64-sl1-work-around-mistakenly-written-eFUSEs.patch \
           file://0201-Ethernet-reconnection-fix.patch \
           file://0301-sunxi-Add-misc-EPROBE_DEFER-checks-to-avoid-misleadi.patch \
           file://0401-pwm-sun4i-Avoid-waiting-until-the-next-period.patch \
           file://0501-spi-sun6i-always-set-parent-to-200-MHz.patch \
           file://0701-iio-adc-add-mcp3221-driver.patch \
           file://0801-i2s.patch \
           file://0901-prusa-boot-logo.patch \
           file://1000-arm64-dts-allwinner-a64-set-GPU-clock-to-432-MHz.patch \
           file://1001-dts-create-sun50i-a64-prusa64-sl1-dts.patch \
           file://1002-arm64-dts-Add-timer-erratum-property-for-Allwinner-A.patch \
           file://1003-arm64-dts-allwinner-a64-add-r_uart-node.patch \
           file://1004-dts-added-prusa64-sl2.patch \
           file://1005-SL2-pin-names.patch \
           file://1006-drop-unused-mux.patch \
           file://1007-uart1-enabled.patch \
           file://1008-prusa64-sl2-dts-LAN8742A-PHY-w-bitbanged-MDIO.patch \
           file://1009-dts-remap-wifi-pins.patch \
           file://1010-drop-i2s-enable-soundcard.patch \
           file://1011-enable-SPI1.patch \
           file://1012-control-panel-with-backlight.patch \
           file://1013-remap-pcf8563-to-i2c2-55.patch \
	   file://1014-add_adxl345_to_spi1.patch \
           "

S="${WORKDIR}/git"

FILES:${KERNEL_PACKAGE_NAME}-devicetree:append = " /${KERNEL_IMAGEDEST}/**/*.dtb"

FW_DIR = "${RECIPE_SYSROOT}${nonarch_base_libdir}/firmware"

do_configure[depends] += "sla-edid:do_populate_sysroot"

do_configure:prepend() {
	if [ "${@bb.utils.filter('DISTRO_FEATURES', 'ld-is-gold', d)}" ]; then
		sed -i 's/$(CROSS_COMPILE)ld$/$(CROSS_COMPILE)ld.bfd/g' ${S}/Makefile
	fi
	sed -i ${WORKDIR}/defconfig -e '/CONFIG_EXTRA_FIRMWARE/d'
	extra_fw=`find ${FW_DIR} -type f | sed "s#^${FW_DIR}/##" | tr '\n' ' '`
	echo "CONFIG_EXTRA_FIRMWARE=\"${extra_fw}\"" >> ${WORKDIR}/defconfig
	echo "CONFIG_EXTRA_FIRMWARE_DIR=\"${FW_DIR}\"" >> ${WORKDIR}/defconfig
	rm -f ${B}/.config
}

do_install:append() {
	rm -f ${D}/${KERNEL_IMAGEDEST}/vmlinux-${KERNEL_VERSION}
}
