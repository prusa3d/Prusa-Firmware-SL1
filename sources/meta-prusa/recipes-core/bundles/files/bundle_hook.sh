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

		# Copy system settings
		cp -av /etc/machine-id ${RAUC_SLOT_MOUNT_POINT}/
		cp -av /etc/hostname ${RAUC_SLOT_MOUNT_POINT}/
		cp -av /etc/locale.conf ${RAUC_SLOT_MOUNT_POINT}/
		cp -av /etc/localtime ${RAUC_SLOT_MOUNT_POINT}/
		test -f /etc/systemd/system/sysinit.target.wants/systemd-timesyncd.service || rm -f ${RAUC_SLOT_MOUNT_POINT}/systemd/system/sysinit.target.wants/systemd-timesyncd.service

		# Copy network settings
		mkdir -p ${RAUC_SLOT_MOUNT_POINT}/NetworkManager/system-connections
		cp -av /etc/NetworkManager/system-connections/*.nmconnection ${RAUC_SLOT_MOUNT_POINT}/NetworkManager/system-connections/

		# Copy printer settings
		cp -av /etc/sl1fw ${RAUC_SLOT_MOUNT_POINT}/
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
