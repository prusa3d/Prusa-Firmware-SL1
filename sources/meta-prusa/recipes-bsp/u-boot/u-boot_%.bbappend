FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
	file://0001-boot-process.patch \
	file://0002-archtimer-freescale-fix.patch \
	file://0003-Ethernet-reconnection-fix.patch \
	file://0004-grow-partition-on-SD-card-to-its-full-extent.patch \
	file://0005-backlight.patch \
	file://0006-exposure-panel.patch \
	file://0007-tc358870-only-proceed-with-setup-if-sl1-uv-led-found.patch \
	file://0008-Kconfig-remove-unnecessary-dependencies.patch \
	file://0009-Revert-cmd-exit-Fix-return-value.patch \
	file://0010-assert-PWR_EN-high-early-SPL.patch \
	file://1001-prusa64-sl1-dts-and-defconfig.patch \
	file://1002-prusa64-sl2-dts-and-defconfig.patch \
	file://1003-prusa64-sl2-Boot-stability-fix.patch \
	file://fw_env.config \
	file://sunxi-prusa.h \
"

PACKAGECONFIG[atf] = "BL31=${STAGING_DIR_HOST}/boot/bl31.bin,,arm-trusted-firmware"
PACKAGECONFIG:append:sun50i = " atf"

EXTRA_OEMAKE:append += " ${PACKAGECONFIG_CONFARGS}"

do_copy_sunxi_prusa_config_header() {
	cp ${WORKDIR}/sunxi-prusa.h ${S}/include/configs/sunxi-prusa.h
}

addtask copy_sunxi_prusa_config_header after do_unpack before do_configure
