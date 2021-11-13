FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "\
	file://0005-do-not-populate-machine-id.patch;patchdir=${WORKDIR} \
"