FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
	file://0001-enable-all-sysrq-commands.patch \
	file://0002-timesyncd-google.patch \
	file://0003-user-no-network-wait.patch \
"
