FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " \
	file://0001-dts-and-defconfig.patch \
	file://0002-hdmi-edid.patch \
	file://0003-boot-process.patch \
	file://0004-mmc-legacy-only.patch \
	file://0005-mmc-delay-1500.patch \
	file://0006-archtimer-freescale-fix.patch \
	file://0007-gpt-add-subcommands.patch \
	file://0008-readme.patch \
	file://0009-Ethernet-reconnection-fix.patch \
	file://0010-concat-bootargs-keep-extra.patch \
	file://0011-grow-partition-on-SD-card-to-its-full-extent.patch \
	file://0012-backlight.patch \
	file://fw_env.config \
"

PACKAGECONFIG[atf] = "BL31=${STAGING_DIR_HOST}/boot/bl31.bin,,arm-trusted-firmware"
PACKAGECONFIG_append_sun50i = " atf"

EXTRA_OEMAKE_append += " ${PACKAGECONFIG_CONFARGS}"

SPL_BINARY = "spl/sunxi-spl.bin"
