FILESEXTRAPATHS_prepend := "${THISDIR}/atf:"
SRC_URI_append = " file://0001-bl31-ld.patch"

EXTRA_OEMAKE_append = " LD=${TARGET_PREFIX}ld.bfd"
