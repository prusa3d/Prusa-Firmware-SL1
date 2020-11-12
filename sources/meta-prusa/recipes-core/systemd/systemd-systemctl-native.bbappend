FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
	file://0005-do-not-populate-machine-id.patch;patchdir=${WORKDIR} \
"