# Simple initramfs image artifact generation for tiny images.
DESCRIPTION = ""

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://boot.cmd"

inherit image

PACKAGE_INSTALL = ""
ROOTFS_BOOTSTRAP_INSTALL = ""
KERNELDEPMODDEPEND = ""

IMAGE_FSTYPES = "wic wic.bmap"
WIC_CREATE_EXTRA_ARGS = "--no-fstab-update"
WKS_FILE_DEPENDS = "virtual/bootloader dosfstools-native bmap-tools-native gptfdisk-native"
WKS_FILE = "sla-bootstrap.wks"

DEPENDS += "u-boot-tools-native"

UBOOT_ENV := "${THISDIR}/files/boot.cmd"

do_rootfs[depends] = "sla-image:do_image_complete u-boot:do_deploy"
do_rootfs() {
	install -d ${IMAGE_ROOTFS}
	dd if=${DEPLOY_DIR_IMAGE}/${SPL_BINARY} of=${IMAGE_ROOTFS}/boot.img bs=1k
	dd if=${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY} of=${IMAGE_ROOTFS}/boot.img bs=1k seek=160

	install -m 0644 ${DEPLOY_DIR_IMAGE}/sla-image-${MACHINE}.root.ext4 ${IMAGE_ROOTFS}/root.img
	install -m 0644 ${DEPLOY_DIR_IMAGE}/sla-image-${MACHINE}.etc.ext4 ${IMAGE_ROOTFS}/etc.img
	install -m 0644 ${DEPLOY_DIR_IMAGE}/sla-image-${MACHINE}.factory.ext4 ${IMAGE_ROOTFS}/factory.img
	install -m 0644 ${DEPLOY_DIR_IMAGE}/uboot.env ${IMAGE_ROOTFS}/uboot.env

	mkimage -C none -A arm -T script -d ${UBOOT_ENV} ${IMAGE_ROOTFS}/boot.scr;
}
IMAGE_NAME = "${IMAGE_BASENAME}-${MACHINE}-${DISTRO_VERSION}${IMAGE_VERSION_SUFFIX}"
