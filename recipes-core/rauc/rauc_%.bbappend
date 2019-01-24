FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " \
	file://0001-accept-bin-as-a-valid-suffix-for-emmc-bootloader-update.patch \
	file://0002-when-extcsd-rw-fails-try-once-more-before-bailing-out.patch \
	file://system.conf \
"
