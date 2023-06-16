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

		# update http digest (default is enabled)
		NGINX_FILE_HTTP_DIGEST=/etc/nginx/sites-available/sl1fw_http_digest
		NGINX_SITE_ENABLED_LINK=/etc/nginx/sites-enabled/sl1fw
		NGINX_FILE_ENABLE=/etc/nginx/http_digest_enabled
		NGINX_ENABLED=true
		FW_VERSION=`awk -F= '$1=="VERSION_ID" { print $2 ;}' /etc/os-release | sed -E 's/^\"?([0-9]+)\.([0-9]+)\.([0-9]+).*/\1\2\3/'`
		echo "Current system version: ${FW_VERSION}"
		# 1.6.4+
		# Script `/usr/bin/prusa-link_enable_nginx_site` checks the printer model and NGINX_FILE_ENABLE.
		# Afterwards it creates site file from /etc/nginx/sites-available/prusa-link.conf.template.
		# Nginx always serves the site from /etc/nginx/sites-available/prusa-link.
		if [ ${FW_VERSION} -ge "164" ]; then
			if [ ! -f ${NGINX_FILE_ENABLE} ]; then
				NGINX_ENABLED=false
			fi
		fi
		# 1.6.0 - 1.6.3
		# There is a site /etc/nginx/sites-available folder for each security option (API key, http digest).
		# NGINX_SITE_ENABLED_LINK points to one of the available sites according to selected security.
		if [ ${FW_VERSION} -ge "160" ] && [ ${FW_VERSION} -le "163" ]; then
			if  [ -f ${NGINX_FILE_HTTP_DIGEST} ] && [[ $(readlink -f ${NGINX_SITE_ENABLED_LINK} ) != ${NGINX_FILE_HTTP_DIGEST} ]]; then
				NGINX_ENABLED=false
			fi
		fi
		# 1.5.0
		# Site file is modified by enabling or commenting out one line.
		# The only thing what we can do is to check if the line is commented out or not.
		if [ ${FW_VERSION} -eq "150" ]; then
			if  [[ $(grep '# include /etc/nginx/prusa-auth.conf;' ${NGINX_SITE_ENABLED_LINK}) ]]; then
				NGINX_ENABLED=false
			fi
		fi
		# <1.5.0
		# No security was required due to readonly Prusa Link. Let the http digest be enabled by default.
		if [ "${NGINX_ENABLED}" = false ] ; then
			echo "Disabling http digest"
			rm -f ${RAUC_SLOT_MOUNT_POINT}/nginx/http_digest_enabled
		else
			echo "Http digest enabled by default"
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
	fi;
	;;
*)
	exit 1
	;;
esac
exit 0
