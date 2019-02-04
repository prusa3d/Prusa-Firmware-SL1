FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " \
	file://mmcdevselect.patch \
	file://boot-offset-sd-compat.patch \
"
