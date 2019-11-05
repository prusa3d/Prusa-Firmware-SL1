inherit bundle

SRC_URI += " \
	file://bundle_hook.sh \
"

LICENSE = "CLOSED"

BUNDLE_NAME = "${BUNDLE_BASENAME}-${MACHINE}-${DISTRO_VERSION}"

UBOOT_WITH_SPL="padded-u-boot-with-spl.bin"

#RAUC_BUNDLE_COMPATIBLE = "prusa64-sl1"
RAUC_BUNDLE_SLOTS = "rootfs bootloader"
RAUC_BUNDLE_HOOKS[file] ?= "bundle_hook.sh"
RAUC_SLOT_rootfs = "sla-image"
RAUC_SLOT_rootfs[fstype] = "root.ext4"
RAUC_SLOT_rootfs[hooks] = "post-install"
RAUC_SLOT_bootloader = "u-boot"
RAUC_SLOT_bootloader[type] = "boot"
RAUC_SLOT_bootloader[file] = "${UBOOT_WITH_SPL}"
RAUC_SLOT_bootloader[hooks] = "post-install"
RAUC_KEY_FILE ?= "${THISDIR}/../../files/ca.key.pem"
RAUC_CERT_FILE ?= "${THISDIR}/../../files/ca.cert.pem"

do_unpack[depends] += "u-boot:do_deploy"
do_bundle[depends] += "u-boot:do_deploy"

compose_uboot() {
	UBOOT_FILE=${DEPLOY_DIR_IMAGE}/${UBOOT_WITH_SPL}
	dd if=${DEPLOY_DIR_IMAGE}/${SPL_BINARY} of=${UBOOT_FILE} bs=1k
	dd if=${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY} of=${UBOOT_FILE} bs=1k seek=160
}

do_unpack[prefuncs] += "compose_uboot"

do_bundle() {
	install -m 644 ${DEPLOY_DIR_IMAGE}/setenv.scr ${BUNDLE_DIR}/
	export OPENSSL_ENGINES=${STAGING_LIBDIR_NATIVE}/engines-1.1
	export RAUC_PKCS11_MODULE=${RAUC_PKCS11_MODULE}
	export PKCS11_PROXY_SOCKET=${PKCS11_PROXY_SOCKET}
	export RAUC_PKCS11_PIN=${RAUC_PKCS11_PIN}

	for val in ${RAUC_INTERMEDIATE_FILE}; do
		INTERMEDIATE_FILE="${INTERMEDIATE_FILE} --intermediate=${val}"
	done

	${STAGING_DIR_NATIVE}${bindir}/rauc bundle \
		--debug \
		--cert=${RAUC_CERT_FILE} \
		--key=${RAUC_KEY_FILE} \
		${INTERMEDIATE_FILE} \
		${BUNDLE_DIR} \
		${B}/bundle.raucb
}
