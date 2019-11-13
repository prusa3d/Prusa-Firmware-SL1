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
		prepare_fs /dev/mmcblk2p6 # /var
	fi;
	if [ "$RAUC_SLOT_CLASS" = "etcfs" ]; then
		/lib/systemd/systemd-growfs ${RAUC_MOUNT_POINT}

		# Do NOT copy settings from the NEWER(1.3) etc
		##############################################
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
