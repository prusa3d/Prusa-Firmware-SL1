FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
        file://hotspot_scan.patch \
"

FILES_${PN} += "\
	${libdir}/systemd/system \
"

PACKAGECONFIG_remove_pn-networkmanager = "polkit"

EXTRA_OECONF += "--with-dbus-sys-dir=/usr/share/dbus-1/system.d"

do_install_append() {
	install -d ${D}${libdir}/systemd/system/multi-user.target.wants
	ln -s ${libdir}/systemd/system/NetworkManager.service ${D}${libdir}/systemd/system/multi-user.target.wants/NetworkManager.service

	install -d ${D}${libdir}/systemd/system/network-online.target.wants
	ln -s ${libdir}/systemd/system/NetworkManager-wait-online.service ${D}${libdir}/systemd/system/network-online.target.wants/NetworkManager-wait-online.service
}
