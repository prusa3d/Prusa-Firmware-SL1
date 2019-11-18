DESCRIPTION = "Extra CMake Modules"
HOMEPAGE = "https://api.kde.org/frameworks/extra-cmake-modules/html/index.html"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING-CMAKE-SCRIPTS;md5=54c7042be62e169199200bc6477f04d1"
PR = "r0"

DEPENDS = "cmake-native qttools"

SRC_URI = "git://anongit.kde.org/extra-cmake-modules;nobranch=1"
S = "${WORKDIR}/git"
CLEANBROKEN = "1"

do_compile() {
    cmake -DCMAKE_INSTALL_PREFIX:PATH=${D}/${prefix}/ .
    make
}

do_install() {
    make install
}

FILES_${PN}-dev += " ${datadir}/ECM"

BBCLASSEXTEND = "native nativesdk"
