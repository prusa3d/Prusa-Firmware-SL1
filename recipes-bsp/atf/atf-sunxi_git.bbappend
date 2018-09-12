FILESEXTRAPATHS_prepend := "${THISDIR}/atf:"
SRC_URI_append = " file://0001-bl31-ld.patch"

EXTRA_OEMAKE_append = " LD=${TARGET_PREFIX}ld.bfd"

SRCREV = "c9f55c023164a6c8c49f70f7ac6c68c626839d6f"

