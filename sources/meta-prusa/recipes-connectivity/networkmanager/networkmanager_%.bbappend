FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "\
	file://disable-firewall.conf \
	file://mdns.conf \
	file://wifi.conf \
"

FILES:${PN} += "\
	${systemd_system_unitdir}/multi-user.target.wants/NetworkManager.service \
	${systemd_system_unitdir}/network-online.target.wants/NetworkManager-wait-online.service \
	${noarch_libdir}/NetworkManager/conf.d/ \
"

PACKAGECONFIG:remove:pn-networkmanager = "polkit dhclient ifupdown"
PACKAGECONFIG:append:pn-networkmanager = "nmcli"

EXTRA_OECONF += "--with-dbus-sys-dir=/usr/share/dbus-1/system.d"

# prevent networkmanager from overtaking of /etc/resolv.conf from systemd-resolved
ALTERNATIVE_PRIORITY = "0"

do_install:append() {
	install -d ${D}${systemd_system_unitdir}/multi-user.target.wants
	ln -s ../NetworkManager.service ${D}${systemd_system_unitdir}/multi-user.target.wants/NetworkManager.service

	install -d ${D}${systemd_system_unitdir}/network-online.target.wants
	ln -s ../NetworkManager-wait-online.service ${D}${systemd_system_unitdir}/network-online.target.wants/NetworkManager-wait-online.service

	install -d ${D}${libdir}/NetworkManager/conf.d
	install -m 644 ${WORKDIR}/disable-firewall.conf ${D}${libdir}/NetworkManager/conf.d/
	install -m 644 ${WORKDIR}/mdns.conf ${D}${libdir}/NetworkManager/conf.d/
	install -m 644 ${WORKDIR}/wifi.conf ${D}${libdir}/NetworkManager/conf.d/


}
