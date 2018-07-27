LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d3f0a6a1cc385ac4e4505bbe577651f9"

SRC_URI = "https://github.com/${BPN}-shell/${BPN}-shell/archive/${PV}.tar.gz \
		file://stat.patch"
SRC_URI[md5sum] = "871811b6e372a7637d1ea3fb8c6d9849"
SRC_URI[sha256sum] = "a12d3877eacb941e3212be323adbf396a80d9c1f843ddc94de427f3dfe81c7ad"

S = "${WORKDIR}/${BPN}-shell-${PV}"
DEPENDS = "ncurses"
#RDEPENDS_${PN} = "man-pages"
inherit autotools-brokensep

EXTRA_OECONF = "--without-gettext "

