require u-boot-srcrev.inc

SRC_URI:append = " \
	file://0001-dts-and-defconfig.patch \
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
	file://0013-exposure-panel-identification.patch \
	file://0014-include-edid_bootarg-in-default-env.patch \
	file://0015-tc358870-switch-VC-if-requested-panel-IC.patch \
	file://0016-lib-sscanf-add-sscanf-implementation.patch \
	file://0016-tc358870-nvm-write.patch \
	file://0017-rauc-update-toggle-eMMC-bootpart-on-slot-flip.patch \
	file://fw_env.config \
"

PACKAGECONFIG[atf] = "BL31=${STAGING_DIR_HOST}/boot/bl31.bin,,arm-trusted-firmware"
PACKAGECONFIG:append:sun50i = " atf"

EXTRA_OEMAKE:append += " ${PACKAGECONFIG_CONFARGS}"
