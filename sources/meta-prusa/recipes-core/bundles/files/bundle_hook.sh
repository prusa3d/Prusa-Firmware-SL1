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
		rsync --archive --delete --verbose /etc/ ${RAUC_MOUNT_PREFIX}/etc/

		rm -f ${RAUC_MOUNT_PREFIX}/etc/dnsmasq.conf
		sed -i 's/#DNSStubListener=udp/DNSStubListener=no/' ${RAUC_MOUNT_PREFIX}/etc/systemd/resolved.conf
		rm -f ${RAUC_MOUNT_PREFIX}/etc/systemd/system/sockets.target.wants/sshd.socket
		ln -sf /dev/null ${RAUC_MOUNT_PREFIX}/etc/systemd/system/sshd.socket

		# Now default is set in /usr/lib/systemd/journald.conf.d/max_use.conf
		sed -i 's/SystemMaxUse=.*/#SystemMaxUse=/' ${RAUC_MOUNT_PREFIX}/etc/systemd/journald.conf

		# Now we use NetworkManager, direct wpa_supplicant service must be disabled
		systemctl --root ${RAUC_MOUNT_PREFIX} disable wpa_supplicant@wlan0.service

		#remove old certificates
		rm -rf ${RAUC_MOUNT_PREFIX}/etc/rauc

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
