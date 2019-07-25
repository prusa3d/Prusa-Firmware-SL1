LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README.md;md5=dc83ce421b74e98abb9beb7b967506e6"
inherit cmake_qt5 qmake5_base

DEPENDS += "qtbase qt3d qtdeclarative qttools"
#qt3d

SRC_URI = "git://github.com/mkeeter/${BPN}.git;protocol=git;branch=master \
	file://cmake.patch \
	file://gles.patch \
	file://anim.patch \
"

SRCREV = "3ae0e68dcba676c537587e4ad28bcbe2caf57b6a"
PV = "0.9.3"
S = "${WORKDIR}/git"
EXTRA_OECMAKE += " -DCMAKE_QT5_EX_PATH_HOST_HEADERS=${STAGING_INCDIR}"
FILES_${PN}-dev += "${datadir}/cmake ${libdir}/cmake"

