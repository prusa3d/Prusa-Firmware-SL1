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

enter_init() {
	grep -w initramfs_shell /proc/cmdline > /dev/null 2>&1 && panic
	umount /sys /dev /proc
	exec switch_root /sysroot /lib/systemd/systemd
}

mkdir -p /dev /sysroot /proc /sys
mount -t proc none /proc
mount -t sysfs none /sys
mount -t devtmpfs none /dev

root_dev=$(awk -F= -v RS=" " '/^root=/ {print $2}' /proc/cmdline)
if ! grep 'rauc\.slot=[AB]' /proc/cmdline > /dev/null 2>&1
then
	mount ${root_dev} /sysroot
	enter_init
fi

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
part_factory=/dev/mmcblk2p7

fsck.ext4 -pv $part_root
fsck.ext4 -pv $part_etc

fsck.ext4 -pv $part_var
if [ $? -eq 4 -o $? -eq 8 ]
then
	mkfs.ext4 -F $part_var -E lazy_itable_init=0
fi

if test -b $part_factory
then
	fsck.ext4 -pv $part_factory
	if [ $? -eq 4 -o $? -eq 8 ]
	then
		mkfs.ext4 -F $part_factory -E lazy_itable_init=0
	fi
fi

mount $part_root /sysroot

if test ! -e /sysroot/skip-mounts
then
	mount $part_etc /sysroot/etc
	mount $part_var /sysroot/var
fi

enter_init
