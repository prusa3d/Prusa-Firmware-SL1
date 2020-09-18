FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append = " \
	file://0001-add-lock-option-to-allow-running-inside-a-sandbox.patch \
	file://0002-use-target_link_libraries-for-linking-Zlib.patch \
"

EXTRA_OECMAKE_append = " -DBUILD_DOC=OFF"
