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
"
