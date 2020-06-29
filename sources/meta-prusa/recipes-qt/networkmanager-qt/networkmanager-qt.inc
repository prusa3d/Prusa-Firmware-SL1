# Package modified
DESCRIPTION = "NetworkManagerQt"
HOMEPAGE = "https://api.kde.org/frameworks/networkmanager-qt/html/index.html"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"
PR = "r0"

DEPENDS += "networkmanager"

SRC_URI = "\
	git://invent.kde.org/frameworks/networkmanager-qt.git;protocol=https;nobranch=1 \
	file://0001-mkspecs-fix.patch \
"
S = "${WORKDIR}/git"

inherit cmake_kf5

RDEPENDS_${PN} += "networkmanager"

