inherit bundle

SRC_URI += " \
	file://bundle_hook.sh \
"

LICENSE = "CLOSED"

BUNDLE_NAME = "${BUNDLE_BASENAME}-${MACHINE}-${DISTRO_VERSION}-${DATETIME}"

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
	export RAUC_PKCS11_PIN=${RAUC_PKCS11_PIN}

	if [ -z "${RAUC_KEY_FILE}" ]; then
		bbfatal "'RAUC_KEY_FILE' not set. Please set to a valid key file location."
	fi

	if [ -z "${RAUC_CERT_FILE}" ]; then
		bbfatal "'RAUC_CERT_FILE' not set. Please set to a valid certificate file location."
	fi

	if [ -n "${RAUC_INTERMEDIATE_FILE}" ]; then
		rauc_intermediate_file=$(echo ${RAUC_INTERMEDIATE_FILE} | sed -e 's/ / \-\-\intermediate\=/;s/^/\-\-intermediate\=/')
	fi


	if [ -e ${B}/bundle.raucb ]; then
		rm ${B}/bundle.raucb
	fi

	${STAGING_DIR_NATIVE}${bindir}/rauc bundle \
		--debug \
		--cert=${RAUC_CERT_FILE} \
		--key=${RAUC_KEY_FILE} \
		${rauc_intermediate_file} \
		${BUNDLE_DIR} \
		${B}/bundle.raucb
}
