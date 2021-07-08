/* SPDX-License-Identifier: GPL-2.0+ */
/*
 * (C) Copyright 2021
 * Prusa Research, a.s. <www.prusa3d.com>
 * Roman Beranek <roman.beranek@prusa3d.com>
 */

#ifndef _SUNXI_PRUSA_CONFIG_H
#define _SUNXI_PRUSA_CONFIG_H

#define SCAN_DEV_FOR_FDT									\
	"scan_dev_for_fdt="									\
		"for prefix in ${boot_prefixes}; do "						\
			"if test ! -e mmc ${mmc_bootdev}:${mmc_bootpart} ${prefix}${fdtfile}; " \
			"then continue; " \
			"fi; " \
			"setenv boot_prefix ${prefix}; "				\
			"if load mmc ${mmc_bootdev}:${mmc_bootpart} ${fdt_addr_r} ${prefix}${fdtfile}; " \
			"then echo LOAD_FDT_OK; exit; "\
			"else echo LOAD_FDT_FAIL; reset; " \
			"fi;" \
		"done\0"


#define UPDATE_ACTIVE_ROOTFS_SLOT \
	"update_active_rootfs_slot=" \
		"test -n \"${BOOT_ORDER}\" || setenv BOOT_ORDER \"A B\"; " \
		"test -n \"${BOOT_A_LEFT}\" || setenv BOOT_A_LEFT 3; " \
		"test -n \"${BOOT_B_LEFT}\" || setenv BOOT_B_LEFT 3; " \
		"setenv boot_prefixes /boot/; " \
		"setenv slot; " \
		"setenv rootlabel; " \
		"setenv tmp; " \
		"for BOOT_SLOT in ${BOOT_ORDER}; do " \
			"" \
			"setenv boots_left \\\\${BOOT_${BOOT_SLOT}_LEFT}; " \
			"setexpr num_slot ${BOOT_SLOT} - a; " \
			"setenv evaluate_boot_order \"" \
				"if test ${boots_left} -le 0; then exit; fi; " \
				"setenv slot ${BOOT_SLOT}; " \
				"setexpr BOOT_${BOOT_SLOT}_LEFT ${boots_left} - 1; " \
				"setenv rootlabel rootfs.${num_slot}; " \
				"\"; " \
			"run evaluate_boot_order; " \
			"if test \"${tmp} ${BOOT_SLOT}\" = \"${BOOT_ORDER}\"; then " \
				"setenv BOOT_ORDER ${BOOT_SLOT} ${tmp}; " \
				"setenv BOOT_${tmp}_LEFT 3; " \
				"if mmc toggle-bootpart 1; then "  \
					"setenv BOOT_${BOOT_SLOT}_LEFT 3; " \
					"env delete tmp evaluate_boot_order slot rootlabel boots_left; "  \
					"saveenv; " \
					"reset; " \
				"fi; " \
			"fi; " \
			"if test -n \"${slot}\"; " \
			"then " \
				"saveenv; " \
				"exit; " \
			"fi; " \
			"setenv tmp ${BOOT_SLOT}; "\
		"done; " \
		"saveenv; " \
		"run update_active_rootfs_slot; " \
	"\0"

#define PRUSA_KERNEL_CMDLINE \
	"rootwait panic=10 video=HDMI-A-1:D ${edid_bootarg} vt.global_cursor_default=0 " \
	"console=${console} board_revision=${board_revision} stmmac.quirks=${eth_quirks}"

#define DEFAULT_EDID_BOOTARG \
	"edid_bootarg=" \
		"drm.edid_firmware=HDMI-A-1:edid/ls055r1sx04_148.5mhz.bin" \
	"\0" \
	"panel_det_ready=1" \
	"\0"

#define BOOTCMD_PRUSA \
	"bootcmd_prusa=" \
		"if test ${mmc_bootdev} -eq 0; then " \
			"setenv rootarg root=/dev/mmcblk0p1 rauc.external; " \
			"setenv mmc_bootpart 1; " \
			"if test -e mmc 0:1 boot.scr; then " \
				"echo Found U-Boot script; " \
				"load mmc 0:1 ${scriptaddr} boot.scr; " \
				"source ${scriptaddr}; " \
			"fi; " \
		"else " \
			"run update_active_rootfs_slot; " \
			"setenv rootarg root=PARTLABEL=${rootlabel} rauc.slot=${slot}; " \
			"gpt part-num mmc 1 ${rootlabel} mmc_bootpart; " \
		"fi; " \
		"run scan_dev_for_fdt; " \
		"" \
		"fdt addr $fdt_addr_r; " \
		"fdt resize 400; " \
		"panel_id setup; " \
		"setenv bootargs ${rootarg} " PRUSA_KERNEL_CMDLINE " ${extra}; " \
		"if load mmc ${mmc_bootdev}:${mmc_bootpart} ${kernel_addr_r} ${boot_prefix}Image; " \
		"then " \
			"echo LOAD_KERNEL_OK; " \
		"else " \
			"reset; " \
		"fi; " \
		"booti ${kernel_addr_r} - ${fdt_addr_r};" \
		"\0"


#define UUID_GPT_OTHER "0fc63daf-8483-4772-8e79-3d69d8477de4"

#undef PARTS_DEFAULT
#define PARTS_DEFAULT \
	"name=environment,start=512K,size=512K,type=${uuid_gpt_other};"		\
	"name=rootfs.0,start=4M,size=768M,type=${uuid_gpt_system};"		\
	"name=rootfs.1,start=772M,size=768M,type=${uuid_gpt_system};"		\
	"name=etc.0,start=1540M,size=32M,type=${uuid_gpt_other};"		\
	"name=etc.1,start=1572M,size=32M,type=${uuid_gpt_other};"		\
	"name=var,start=1668M,size=2000M,type=${uuid_gpt_other};"		\
	"name=factory,start=1604M,size=64M,type=${uuid_gpt_other};"		\

#define PRUSA_ENV_SETTINGS \
	"uuid_gpt_other=" UUID_GPT_OTHER "\0" \
	SCAN_DEV_FOR_FDT \
	UPDATE_ACTIVE_ROOTFS_SLOT \
	DEFAULT_EDID_BOOTARG \
	BOOTCMD_PRUSA \

#endif /* _SUNXI_PRUSA_CONFIG_H */
