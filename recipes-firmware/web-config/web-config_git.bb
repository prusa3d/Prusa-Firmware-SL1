SUMMARY = "web config - captive cportal for wireless configuration" 

LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/web-setup.git;protocol=ssh;branch=master \
"
SRCREV_pn-${PN} = "a36324294dcdb4ebb76a8ff03a4a203042b5383a"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "dnsmasq hostapd iptables avahi-daemon avahi-restarter python3-cherrypy python3-pydbus iw nftables python3-logging python3-jinja2"

S="${WORKDIR}/git"

FILES_${PN} += "\
	/srv/http/webconfig \
	${sysconfdir}/nginx/sites-enabled/webconfig \
"

inherit setuptools3 systemd

do_install_append () {
	# Enable nginx site
	install -d ${D}${sysconfdir}/nginx/sites-enabled
	ln -s ${sysconfdir}/nginx/sites-available/webconfig ${D}${sysconfdir}/nginx/sites-enabled/webconfig
	
	# Remove empty /usr/share
	rmdir ${D}/usr/share
}

SYSTEMD_SERVICE_${PN} = "wifi-config.service"
