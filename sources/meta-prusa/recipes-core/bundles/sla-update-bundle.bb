LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

inherit bundle uboot-config

SRC_URI += " \
	file://bundle_hook.sh \
"


BUNDLE_NAME = "${BUNDLE_BASENAME}-${MACHINE}-${DISTRO_VERSION}"
BUNDLE_ARGS += '--conf= \
	--mksquashfs-args="-b 1M -comp xz" \
'

#RAUC_BUNDLE_COMPATIBLE = "prusa64-sl1"
RAUC_BUNDLE_VERSION="${DISTRO_VERSION}"
RAUC_BUNDLE_SLOTS = "rootfs bootloader etcfs"
RAUC_BUNDLE_HOOKS[file] ?= "bundle_hook.sh"
RAUC_SLOT_rootfs = "sla-image"
RAUC_SLOT_rootfs[fstype] = "root.ext4"
RAUC_SLOT_rootfs[hooks] = "post-install"
RAUC_SLOT_etcfs = "sla-image"
RAUC_SLOT_etcfs[fstype] = "etc.ext4"
RAUC_SLOT_etcfs[hooks] = "post-install"
RAUC_SLOT_bootloader = "u-boot"
RAUC_SLOT_bootloader[type] = "boot"
RAUC_SLOT_bootloader[file] = "${UBOOT_BINARY}"
RAUC_SLOT_bootloader[hooks] = "post-install"
RAUC_KEY_FILE ?= "${THISDIR}/../../files/ca.key.pem"
RAUC_CERT_FILE ?= "${THISDIR}/../../files/ca.cert.pem"

do_unpack[depends] += "u-boot:do_deploy"
do_bundle[depends] += "u-boot:do_deploy"

export OPENSSL_ENGINES = "${STAGING_LIBDIR_NATIVE}/engines-1.1"
export PKCS11_PROXY_SOCKET
export RAUC_PKCS11_MODULE
export RAUC_PKCS11_PIN

SSTATE_SKIP_CREATION:task-deploy = '1'
