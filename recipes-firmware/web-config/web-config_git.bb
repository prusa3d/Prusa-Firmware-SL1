SUMMARY = "web config - captive cportal for wireless configuration" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/web-setup.git;protocol=ssh;branch=master"
SRCREV_pn-${PN} = "77ad8ed92c1dc9b75448a467f45d98c8b5e77b55"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "cherrypy dnsmasq hostapd iptables python-dbus"

S="${WORKDIR}/git"

inherit setuptools systemd

SYSTEMD_SERVICE_${PN} = "hostapd-keygen.service captive-portal.service"
