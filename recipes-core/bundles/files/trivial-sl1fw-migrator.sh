#!/bin/sh

case "$1" in
        slot-post-install)
                # only rootfs needs to be handled
                test "$RAUC_SLOT_CLASS" = "rootfs" || exit 0

                cp -av /etc/sl1fw ${RAUC_SLOT_MOUNT_POINT}/etc/							|| true
                cp -av /etc/ssh/ssh_host_{ecdsa,ed25519,rsa}_key{,.pub} ${RAUC_SLOT_MOUNT_POINT}/etc/ssh/	|| true
                cp -av {,${RAUC_SLOT_MOUNT_POINT}}/etc/wpa_supplicant/wpa_supplicant-wlan0.secrets.json		|| true
                cp -av {,${RAUC_SLOT_MOUNT_POINT}}/etc/.updated							|| true
                cp -av {,${RAUC_SLOT_MOUNT_POINT}}/etc/hostapd.secrets.json					|| true
                ;;
        *)
                exit 1
                ;;
esac

exit 0 
