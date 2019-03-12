#!/bin/sh
set -x

PATH=/sbin:/bin:/usr/sbin:/usr/bin

panic() {
        chvt 1
        echo "$@"

        local console rest
        if command -v setsid > /dev/null 2>&1; then
                read console rest < /proc/consoles
                if [ "${console}" = "tty0"]; then console="tty1"; fi
                REASON="$@" PS1='(initramfs) ' setsid sh -c "exec sh -i <>/dev/${console} 1>&0 2>&1"
        else
                REASON="$@" PS1='(initramfs) ' sh -i </dev/console > /dev/console 2>&1
        fi
}

mkdir -p /dev /mnt/root /proc /sys
mount -t proc none /proc
mount -t sysfs none /sys
mount -t devtmpfs none /dev

slot=$(grep -oe 'rauc\.slot=[AB]' /proc/cmdline | cut -d'=' -f2)
case $slot in
A)
	part_root=/dev/mmcblk2p2
	part_etc=/dev/mmcblk2p4
	;;
B)
	part_root=/dev/mmcblk2p3
	part_etc=/dev/mmcblk2p5
	;;
esac
part_var=/dev/mmcblk2p6

fsck.ext4 -pv $part_root
fsck.ext4 -pv $part_etc

fsck.ext4 -pv $part_var
if [ $? -eq 4 -o $? -eq 8 ]
then
	mkfs.ext4 -F $part_var
fi

mount $part_root /mnt/root
mount $part_etc /mnt/root/etc
mount $part_var /mnt/root/var

umount /sys /dev /proc

exec switch_root /mnt/root /lib/systemd/systemd
