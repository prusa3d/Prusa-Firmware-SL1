SUMMARY = "Wifi config - wireless configuration" 

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "\
	git://git@gitlab.com/prusa3d/sl1/captive-portal.git;protocol=ssh;branch=master \
"
SRCREV_pn-${PN} = "85d16aef773962d8d2ae487e0e0b39f27e7c1223"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "dnsmasq python3-pydbus python3-logging python3-asyncio"

FILES_${PN} += "\
	/usr/share/dbus-1/system.d \
"

S="${WORKDIR}/git"

inherit setuptools3 systemd

SYSTEMD_SERVICE_${PN} = "wifi-config.service"
