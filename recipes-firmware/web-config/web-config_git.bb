SUMMARY = "web config - captive cportal for wireless configuration" 

LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/web-setup.git;protocol=ssh;branch=master \
"
SRCREV_pn-${PN} = "9f4944fce87d405dbc1976b225316b43c40c7da6"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "dnsmasq hostapd iptables avahi-daemon avahi-restarter cherrypy python-pydbus iw"

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

SYSTEMD_SERVICE_${PN} = "wifi-config.service"
