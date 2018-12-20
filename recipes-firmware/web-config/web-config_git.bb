SUMMARY = "web config - captive cportal for wireless configuration" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/web-setup.git;protocol=ssh;branch=master"
SRCREV_pn-${PN} = "8a599c05cb44650c9c90b36656771784d17a6f05"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "cherrypy dnsmasq hostapd iptables python-dbus"

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
}
