LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL += "\
	packagegroup-prusa-base \
	packagegroup-prusa-tools \
	packagegroup-prusa-devel \
	packagegroup-prusa-fs \
	packagegroup-prusa-sla \
	klipper g0-mcu \
"

IMAGE_FEATURES += "\
	package-management \
	ssh-server-openssh \
	tools-debug tools-sdk \
	dev-pkgs \
	allow-empty-password \
	empty-root-password \
	post-install-logging \
"

IMAGE_LINGUAS = "en-us"

IMAGE_FSTYPES = "wic wic.bmap"
WIC_CREATE_EXTRA_ARGS = "--no-fstab-update"
WKS_FILE_DEPENDS = "virtual/bootloader e2fsprogs-native bmap-tools-native gptfdisk-native"
WKS_FILE = "sunxi-sd.wks.in"
do_image_wic[depends] += "u-boot:do_deploy"


DEPENDS += "systemd-systemctl-native coreutils-native u-boot-tools-native"

rootfs_enable_ssh_and_serial_unconditionally () {
	systemctl --root=$D enable sshd.socket
	rm -f $D${systemd_system_unitdir}/serial-getty@ttyS0.service.d/condition-enabled.conf
	rm -f $D${systemd_system_unitdir}/sshd.socket.d/condition-enabled.conf
}

UBOOT_DEV_CMD := "${THISDIR}/files/dev.cmd"

rootfs_insert_boot_scr () {
	mkimage -C none -A arm -T script -d ${UBOOT_DEV_CMD} $D/boot.scr
}

ROOTFS_POSTPROCESS_COMMAND += " \
	rootfs_insert_boot_scr ; \
	rootfs_enable_ssh_and_serial_unconditionally ; \
"
IMAGE_NAME = "${IMAGE_BASENAME}-${MACHINE}-${DISTRO_VERSION}${IMAGE_VERSION_SUFFIX}"
