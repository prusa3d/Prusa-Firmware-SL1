FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI:append = " \
	file://0001-add-lock-option-to-allow-running-inside-a-sandbox.patch \
	file://0002-use-target_link_libraries-for-linking-Zlib.patch \
"
RDEPENDS:${PN}:append:class-target = " u-boot-env"

EXTRA_OECMAKE:append = " -DBUILD_DOC=OFF"
