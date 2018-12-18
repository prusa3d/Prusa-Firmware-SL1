SUMMARY = "web config - captive cportal for wireless configuration" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/web-setup.git;protocol=ssh;branch=master"
SRCREV_pn-${PN} = "0bf8d1d750b20523d7fe0fba7c484be95b8f22ac"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "cherrypy dnsmasq hostapd iptables python-dbus"

S="${WORKDIR}/git"

inherit setuptools systemd

SYSTEMD_SERVICE_${PN} = "captive-portal.target"
