FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

require u-boot_common.inc

SRC_URI_append = " file://fw_env.config "

do_install_append() {
	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
