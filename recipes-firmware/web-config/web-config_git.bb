SUMMARY = "web config - captive cportal for wireless configuration" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/web-setup.git;protocol=ssh;branch=master"
SRCREV_pn-${PN} = "5499644c18220aa27cba12575c3474e764280d80"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "cherrypy dnsmasq hostapd iptables python-dbus"

S="${WORKDIR}/git"

inherit setuptools systemd

SYSTEMD_SERVICE_${PN} = "hostapd-keygen.service captive-portal.service"
