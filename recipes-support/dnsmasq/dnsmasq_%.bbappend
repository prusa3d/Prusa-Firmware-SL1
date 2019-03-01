FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "\
	file://dnsmasq.conf \
"

SYSTEMD_AUTO_ENABLE_${PN} = "disable"
