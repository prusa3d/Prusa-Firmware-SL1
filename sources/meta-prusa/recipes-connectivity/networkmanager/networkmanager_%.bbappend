FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
        file://hotspot_scan.patch \
	file://mdns.conf \
	file://wifi.conf \
"

FILES_${PN} += "\
	${systemd_system_unitdir}/multi-user.target.wants/NetworkManager.service \
	${systemd_system_unitdir}/network-online.target.wants/NetworkManager-wait-online.service \
	${noarch_libdir}/NetworkManager/conf.d/ \
"

PACKAGECONFIG_remove_pn-networkmanager = "polkit dhclient dnsmasq ifupdown"

EXTRA_OECONF += "--with-dbus-sys-dir=/usr/share/dbus-1/system.d"

do_install_append() {
	install -d ${D}${systemd_system_unitdir}/multi-user.target.wants
	ln -s ../NetworkManager.service ${D}${systemd_system_unitdir}/multi-user.target.wants/NetworkManager.service

	install -d ${D}${systemd_system_unitdir}/network-online.target.wants
	ln -s ../NetworkManager-wait-online.service ${D}${systemd_system_unitdir}/network-online.target.wants/NetworkManager-wait-online.service

	install -d ${D}${libdir}/NetworkManager/conf.d
	install -m 644 ${WORKDIR}/mdns.conf ${D}${libdir}/NetworkManager/conf.d/
	install -m 644 ${WORKDIR}/wifi.conf ${D}${libdir}/NetworkManager/conf.d/


}
