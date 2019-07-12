FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
        file://hotspot_scan.patch \
"

PACKAGECONFIG_remove_pn-networkmanager = "polkit"
