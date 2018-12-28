SUMMARY = "web config - captive cportal for wireless configuration" 

LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/web-setup.git;protocol=ssh;branch=master \
"
SRCREV_pn-${PN} = "75ef60c54a63d947098dc9958af14504003e2ff2"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "dnsmasq hostapd iptables avahi-daemon avahi-restarter cherrypy python-dbus"

S="${WORKDIR}/git"

FILES_${PN} += "\
	/srv/http/webconfig \
	${sysconfdir}/nginx/sites-enabled/webconfig \
"

inherit setuptools systemd

do_install_append () {
	# Enable nginx site
	install -d ${D}${sysconfdir}/nginx/sites-enabled
	ln -s ${sysconfdir}/nginx/sites-available/webconfig ${D}${sysconfdir}/nginx/sites-enabled/webconfig
	
	# Remove empty /usr/share
	rmdir ${D}/usr/share
}
