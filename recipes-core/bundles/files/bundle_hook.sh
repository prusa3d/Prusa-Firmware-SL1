#!/bin/sh

case "$1" in
	slot-post-install)
		if [ "$RAUC_SLOT_CLASS" = "rootfs" ]; then
			cp -av /etc/sl1fw ${RAUC_SLOT_MOUNT_POINT}/etc/							|| true
			cp -av /etc/ssh/ssh_host_{ecdsa,ed25519,rsa}_key{,.pub} ${RAUC_SLOT_MOUNT_POINT}/etc/ssh/	|| true
			cp -av {,${RAUC_SLOT_MOUNT_POINT}}/etc/wpa_supplicant/wpa_supplicant-wlan0.secrets.json		|| true
			cp -av {,${RAUC_SLOT_MOUNT_POINT}}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf			|| true
			cp -av {,${RAUC_SLOT_MOUNT_POINT}}/etc/hostapd.secrets.json					|| true
			cp -av {,${RAUC_SLOT_MOUNT_POINT}}/etc/hostapd.conf						|| true
			cp -av {,${RAUC_SLOT_MOUNT_POINT}}/etc/.updated							|| true
			cp -av {,${RAUC_SLOT_MOUNT_POINT}}/etc/machine-id						|| true
		fi;
		
		if [ "$RAUC_SLOT_CLASS" = "bootloader" ]; then
			echo "Updating u-boot environment"
			fw_setenv scan_dev_for_fdt 'for prefix in ${boot_prefixes}; do if test -e mmc ${mmc_bootdev}:${rootpart} ${prefix}${fdtfile}; then setenv boot_prefix ${prefix}; if load mmc ${mmc_bootdev}: {rootpart} ${fdt_addr_r} ${prefix}${fdtfile}; then echo LOAD_FDT_OK; else echo LOAD_FDT_FAIL; reset; fi; break; elif test -e mmc ${mmc_bootdev}:${rootpart} ${prefix}${basefdt}; then setenv boot_prefix ${prefix}; if load mmc ${mmc_bootdev}:${rootpart} ${fdt_addr_r} ${prefix}${basefdt}; then echo LOAD_FDT_OK; else echo LOAD_FDT_FAIL; reset; fi; break; fi; done'

			fw_setenv bootcmd 'run bootcmd_prusa'

			fw_setenv bootcmd_prusa_emmc 'run update_active_rootfs_slot; run scan_dev_for_fdt; setenv bootargs console=${console} root=/dev/mmcblk2p${rootpart} rootwait panic=10 video=HDMI-A-1:D drm.edid_firmware=HDMI-A-1:edid/ls055r1sx04_148.5mhz.bin ${extra} logo.nologo vt.global_cursor_default=0 rauc.slot=${slot}; if load mmc 1:${rootpart} ${kernel_addr_r} ${boot_prefix}Image; then echo LOAD_KERNEL_OK; else echo LOAD_KERNEL_FAIL; reset; fi; booti ${kernel_addr_r} - ${fdt_addr_r};'

			fw_setenv bootcmd_prusa 'if test ${mmc_bootdev} -eq 0; then run bootcmd_prusa_sd; else run bootcmd_prusa_emmc; fi;'
		fi;
		;;
	*)
		exit 1
		;;
esac
exit 0
