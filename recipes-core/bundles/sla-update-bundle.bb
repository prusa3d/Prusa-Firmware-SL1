inherit bundle

SRC_URI += " \
	file://trivial-sl1fw-migrator.sh \
"

LICENSE = "CLOSED"

#RAUC_BUNDLE_COMPATIBLE = "prusa64-sl1"
RAUC_BUNDLE_SLOTS = "rootfs bootloader"
RAUC_BUNDLE_HOOKS[file] ?= "trivial-sl1fw-migrator.sh"
RAUC_SLOT_rootfs = "sla-image"
RAUC_SLOT_rootfs[fstype] = "ext4"
RAUC_SLOT_rootfs[hooks] = "post-install"
RAUC_SLOT_bootloader = "u-boot"
RAUC_SLOT_bootloader[type] = "boot"
RAUC_SLOT_bootloader[file] = "${SPL_BINARY}"
RAUC_KEY_FILE = "${THISDIR}/../../files/ca.key.pem"
RAUC_CERT_FILE = "${THISDIR}/../../files/ca.cert.pem"

do_unpack[depends] += "u-boot:do_deploy"

