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
		echo "Rauc mount point: ${RAUC_MOUNT_POINT}"
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
		cp -av /etc/touch-ui ${RAUC_SLOT_MOUNT_POINT}/

		# update http digest (default is enabled)
		NGINX_FILE=/etc/nginx/sites-available/sl1fw
		NGINX_FILE_HTTP_DIGEST=/etc/nginx/sites-available/sl1fw_http_digest
		NGINX_FILE_ENABLED=/etc/nginx/sites-enabled/sl1fw
		NGINX_ENABLED=true
		# 1.6+
		if  [ -f ${NGINX_FILE_HTTP_DIGEST} ] && [[ $(readlink -f ${NGINX_FILE_ENABLED} ) != ${NGINX_FILE_HTTP_DIGEST} ]]; then
			NGINX_ENABLED=false
		fi
		# only to 1.5
		if  [ -f "/etc/sl1fw/remoteConfig.toml" ] && [[ $(awk '/http_digest/ {printf $3}' /etc/sl1fw/remoteConfig.toml) != "true" ]]; then
			NGINX_ENABLED=false
		fi
		if [ "${NGINX_ENABLED}" = false ] ; then
			rm -f ${RAUC_SLOT_MOUNT_POINT}${NGINX_FILE_ENABLED}
			ln -s ${NGINX_FILE} ${RAUC_SLOT_MOUNT_POINT}${NGINX_FILE_ENABLED}
		fi

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

		echo "Updating u-boot environment"
		fw_setenv --config ${RAUC_SLOT_MOUNT_POINT}/fw_env.config --script ${RAUC_SLOT_MOUNT_POINT}/u-boot-initial-env
	fi;
	;;
*)
	exit 1
	;;
esac
exit 0
