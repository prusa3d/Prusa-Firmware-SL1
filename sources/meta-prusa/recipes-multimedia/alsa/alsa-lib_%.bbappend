FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTRA_OECONF:append = " --disable-ucm"
RDEPENDS:${PN}:remove:class-target = "alsa-ucm-conf"

SRC_URI:append = " file://0001-dummy-uc_mgr_alibcfg_by_device-when-ucm-disabled.patch"
