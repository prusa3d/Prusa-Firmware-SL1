SUMMARY = "web config - captive cportal for wireless configuration" 

LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/web-setup.git;protocol=ssh;branch=master \
"
SRCREV_pn-${PN} = "45478c87db61ce4349ff9fb783e39c7cd7c3a7df"

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
