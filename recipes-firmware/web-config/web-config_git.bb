SUMMARY = "web config - captive cportal for wireless configuration" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/web-setup.git;protocol=ssh;branch=master"
SRCREV_pn-${PN} = "bfbec58ed0f560b4f7b3527114e6698fb6dbc050"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "cherrypy dnsmasq hostapd iptables python-dbus"

S="${WORKDIR}/git"

inherit setuptools systemd

SYSTEMD_SERVICE_${PN} = "hostapd-keygen.service captive-portal.service"
