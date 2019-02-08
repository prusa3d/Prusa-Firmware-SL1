inherit bundle

SRC_URI += " \
	file://bundle_hook.sh \
"

LICENSE = "CLOSED"

UBOOT_WITH_SPL="padded-u-boot-with-spl.bin"

#RAUC_BUNDLE_COMPATIBLE = "prusa64-sl1"
RAUC_BUNDLE_SLOTS = "rootfs bootloader"
RAUC_BUNDLE_HOOKS[file] ?= "bundle_hook.sh"
RAUC_SLOT_rootfs = "sla-image"
RAUC_SLOT_rootfs[fstype] = "ext4"
RAUC_SLOT_rootfs[hooks] = "post-install"
RAUC_SLOT_bootloader = "u-boot"
RAUC_SLOT_bootloader[type] = "boot"
RAUC_SLOT_bootloader[file] = "${UBOOT_WITH_SPL}"
RAUC_SLOT_bootloader[hooks] = "post-install"
RAUC_KEY_FILE = "${THISDIR}/../../files/ca.key.pem"
RAUC_CERT_FILE = "${THISDIR}/../../files/ca.cert.pem"

do_unpack[depends] += "u-boot:do_deploy"

compose_uboot() {
	UBOOT_FILE=${DEPLOY_DIR_IMAGE}/${UBOOT_WITH_SPL}
	dd if=${DEPLOY_DIR_IMAGE}/${SPL_BINARY} of=${UBOOT_FILE} bs=1K
	dd if=/dev/zero of=${UBOOT_FILE} bs=1K count=8 oflag=append conv=notrunc
	dd if=${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY} of=${UBOOT_FILE} bs=1K oflag=append conv=notrunc
}

do_unpack[prefuncs] += "compose_uboot"
