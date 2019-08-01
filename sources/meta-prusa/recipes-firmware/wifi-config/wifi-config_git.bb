SUMMARY = "Wifi config - wireless configuration" 

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "\
	git://git@gitlab.com/prusa3d/sl1/captive-portal.git;protocol=ssh;branch=networkmanager \
"
SRCREV_pn-${PN} = "6d67c67ac8eb311b7640a35d652f5132eb9e5b7a"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "dnsmasq python3-pydbus python3-logging python3-asyncio"

S="${WORKDIR}/git"

inherit setuptools3 systemd

do_install_append () {
	# Remove empty /usr/share
	rmdir ${D}/usr/share
}

SYSTEMD_SERVICE_${PN} = "wifi-config.service"
