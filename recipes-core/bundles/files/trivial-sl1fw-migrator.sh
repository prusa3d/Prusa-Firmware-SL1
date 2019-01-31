#!/bin/sh

case "$1" in
        slot-post-install)
                # only rootfs needs to be handled
                test "$RAUC_SLOT_CLASS" = "rootfs" || exit 0

                cp -av /etc/sl1fw/* ${RAUC_SLOT_MOUNT_POINT}/etc/sl1fw/
                ;;
        *)
                exit 1
                ;;
esac

exit 0 
