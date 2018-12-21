SUMMARY = "web config - captive cportal for wireless configuration" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/web-setup.git;protocol=ssh;branch=master"
SRCREV_pn-${PN} = "dc14e94de79e3e9783563e4576e165f8408f641a"

PACKAGES = "${PN}"

DEPENDS = "cherrypy python-dbus" 
RDEPENDS_${PN} = "dnsmasq hostapd iptables avahi-daemon avahi-restarter"

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
