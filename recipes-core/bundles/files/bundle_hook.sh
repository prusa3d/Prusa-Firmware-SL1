#!/bin/sh

function prepare_fs() {
	fsck.ext4 -pv $1
	if [ $? -eq 4 -o $? -eq 8 ]
	then
		mkfs.ext4 $1
	fi
}

case "$1" in
slot-post-install)
	if [ "$RAUC_SLOT_CLASS" = "rootfs" ]; then
		case "${RAUC_SLOT_BOOTNAME}" in
		A)
			etc_dev=/dev/mmcblk2p4
			;;
		B)
			etc_dev=/dev/mmcblk2p5
			;;
		esac
		prepare_fs $etc_dev

		mkdir -p ${RAUC_MOUNT_PREFIX}/etc
		mount ${etc_dev} ${RAUC_MOUNT_PREFIX}/etc
		rsync --archive --delete --verbose /etc/ ${RAUC_MOUNT_PREFIX}/etc/
		rm -f ${RAUC_MOUNT_PREFIX}/etc/dnsmasq.conf
		sed -i 's/#DNSStubListener=udp/DNSStubListener=no/' ${RAUC_MOUNT_PREFIX}/etc/systemd/resolved.conf
		umount ${etc_dev}

		prepare_fs /dev/mmcblk2p6 # /var
	fi;
	if [ "$RAUC_SLOT_CLASS" = "bootloader" ]; then
		echo "Updating u-boot environment"

		fw_setenv scan_dev_for_fdt 'for prefix in ${boot_prefixes}; do if test -e mmc ${mmc_bootdev}:${rootpart} ${prefix}${fdtfile}; then setenv boot_prefix ${prefix}; if load mmc ${mmc_bootdev}:${rootpart} ${fdt_addr_r} ${prefix}${fdtfile}; then echo LOAD_FDT_OK; else echo LOAD_FDT_FAIL; reset; fi; break; elif test -e mmc ${mmc_bootdev}:${rootpart} ${prefix}${basefdt}; then setenv boot_prefix ${prefix}; if load mmc ${mmc_bootdev}:${rootpart} ${fdt_addr_r} ${prefix}${basefdt}; then echo LOAD_FDT_OK; else echo LOAD_FDT_FAIL; reset; fi; break; fi; done'

		fw_setenv bootcmd 'run bootcmd_prusa'

		fw_setenv bootcmd_prusa_emmc 'run update_active_rootfs_slot; run scan_dev_for_fdt; setenv bootargs console=${console} root=/dev/mmcblk2p${rootpart} rootwait panic=10 video=HDMI-A-1:D drm.edid_firmware=HDMI-A-1:edid/ls055r1sx04_148.5mhz.bin ${extra} logo.nologo vt.global_cursor_default=0 rauc.slot=${slot}; if load mmc 1:${rootpart} ${kernel_addr_r} ${boot_prefix}Image; then echo LOAD_KERNEL_OK; else echo LOAD_KERNEL_FAIL; reset; fi; booti ${kernel_addr_r} - ${fdt_addr_r};'

		fw_setenv bootcmd_prusa_sd 'setenv rootpart 1; run scan_dev_for_fdt; setenv bootargs console=${console} root=/dev/mmcblk0p2 rootwait panic=10 video=HDMI-A-1:D drm.edid_firmware=HDMI-A-1:edid/ls055r1sx04_148.5mhz.bin  ${extra} logo.nologo vt.global_cursor_default=0; load mmc 0:${bootpart} ${kernel_addr_r} ${boot_prefix}Image; booti ${kernel_addr_r} - ${fdt_addr_r};'

		fw_setenv bootcmd_prusa 'if test ${mmc_bootdev} -eq 0; then run bootcmd_prusa_sd; else run bootcmd_prusa_emmc; fi;'
	fi;
	;;
*)
	exit 1
	;;
esac
exit 0
