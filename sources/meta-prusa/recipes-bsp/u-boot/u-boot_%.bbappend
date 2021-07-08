require u-boot-srcrev.inc

SRC_URI:append = " \
	file://0001-dts-and-defconfig.patch \
	file://0002-env-reset-defaults-on-load.patch \
	file://0003-boot-process.patch \
	file://0006-archtimer-freescale-fix.patch \
	file://0009-Ethernet-reconnection-fix.patch \
	file://0011-grow-partition-on-SD-card-to-its-full-extent.patch \
	file://0012-backlight.patch \
	file://0013-exposure-panel-identification.patch \
	file://0015-tc358870-switch-VC-if-requested-panel-IC.patch \
	file://0016-lib-sscanf-add-sscanf-implementation.patch \
	file://0016-tc358870-nvm-write.patch \
	file://0018-tc358870-only-proceed-with-setup-if-sl1-uv-led-found.patch \
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
