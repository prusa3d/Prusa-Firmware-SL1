FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " \
	file://mmc-legacy-only.patch \
	file://mmc-delay-1500.patch \
	file://archtimer-freescale-fix.patch \
"
