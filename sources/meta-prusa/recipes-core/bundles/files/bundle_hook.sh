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
		chgrp -R projects /var/sl1fw/projects
	fi;
	if [ "$RAUC_SLOT_CLASS" = "etcfs" ]; then
		echo "Rauc mount point: ${RAUC_SLOT_MOUNT_POINT}"
		/lib/systemd/systemd-growfs ${RAUC_SLOT_MOUNT_POINT}

		# Copy system settings
		cp -av /etc/machine-id ${RAUC_SLOT_MOUNT_POINT}/
		cp -av /etc/hostname ${RAUC_SLOT_MOUNT_POINT}/
		cp -av /etc/locale.conf ${RAUC_SLOT_MOUNT_POINT}/
		cp -av /etc/localtime ${RAUC_SLOT_MOUNT_POINT}/
		systemctl is-active --quiet systemd-timesyncd.service || rm -f ${RAUC_SLOT_MOUNT_POINT}/systemd/system/sysinit.target.wants/systemd-timesyncd.service && rm -f ${RAUC_SLOT_MOUNT_POINT}/systemd/system/dbus-org.freedesktop.timesync1.service

		# Copy network settings
		mkdir -p ${RAUC_SLOT_MOUNT_POINT}/NetworkManager/system-connections
		cp -av /etc/NetworkManager/system-connections/*.nmconnection ${RAUC_SLOT_MOUNT_POINT}/NetworkManager/system-connections/

		# Copy printer settings
		cp -av /etc/sl1fw ${RAUC_SLOT_MOUNT_POINT}/
		cp -av /etc/touch-ui ${RAUC_SLOT_MOUNT_POINT}/

		# Remove obsolete file with API key
		rm -f ${RAUC_SLOT_MOUNT_POINT}/etc/sl1fw/slicer-upload-api.key

		# Copy update channel override
		mkdir -p ${RAUC_SLOT_MOUNT_POINT}/systemd/system/updater.service.d/
		cp -av /etc/systemd/system/updater.service.d/channel.conf ${RAUC_SLOT_MOUNT_POINT}/systemd/system/updater.service.d/channel.conf
		cp /etc/update_channel ${RAUC_SLOT_MOUNT_POINT}/update_channel

		# Set rauc CA
		# Iterate over trust chain SPKI hashes (from leaf to root)
		for i in $RAUC_BUNDLE_SPKI_HASHES; do
				# Test for development certificate (production certificate is preset)
				echo "Current hash: ${i}"
				if [ "$i" == "D1:2A:2A:81:75:64:26:C2:E2:3C:3C:45:53:03:2A:87:6E:BF:1A:78:C0:C7:93:C6:4B:E9:41:40:05:6A:7F:AB" ]; then
						echo "Activating development key chain"
						cp ${RAUC_SLOT_MOUNT_POINT}/rauc/ca-dev.cert.pem ${RAUC_SLOT_MOUNT_POINT}/rauc/ca.cert.pem
						break
				fi
		done
	fi;
	;;
*)
	exit 1
	;;
esac
exit 0
