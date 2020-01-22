#!/bin/sh

function prepare_fs() {
	fsck.ext4 -pv $1
	if [ $? -eq 4 -o $? -eq 8 ]
	then
		mkfs.ext4 -F $1
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
		/lib/systemd/systemd-growfs ${RAUC_MOUNT_PREFIX}/etc

		umount ${etc_dev}

		prepare_fs /dev/mmcblk2p6 # /var
	fi;
	if [ "$RAUC_SLOT_CLASS" = "bootloader" ]; then
		echo "Updating u-boot environment"
		fw_setenv --script ${RAUC_UPDATE_SOURCE}/setenv.scr
	fi;
	;;
*)
	exit 1
	;;
esac
exit 0
