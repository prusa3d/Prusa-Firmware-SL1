FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://base-feeds.conf"

do_install:append() {
	install -d ${D}${sysconfdir}/opkg
	install -m 0644 ${WORKDIR}/base-feeds.conf ${D}${sysconfdir}/opkg/
}

CONFFILES:${PN}:append = " ${sysconfdir}/opkg/base-feeds.conf"
