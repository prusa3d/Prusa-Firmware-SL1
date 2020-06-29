DESCRIPTION = "Extra CMake Modules"
HOMEPAGE = "https://api.kde.org/frameworks/extra-cmake-modules/html/index.html"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING-CMAKE-SCRIPTS;md5=54c7042be62e169199200bc6477f04d1"
PR = "r0"

DEPENDS = "cmake-native qttools"

SRC_URI = " \
	git://invent.kde.org/frameworks/extra-cmake-modules.git;protocol=https;nobranch=1 \
	file://0001-askqmake-warnings.patch \
	file://0003-ecmgeneerateprifile-handle-qt-module-base.patch \
"
#	file://0002-ecmgeneerateprifile-add-option-to-use-qt-module-base.patch
S = "${WORKDIR}/git"
CLEANBROKEN = "1"

ALLOW_EMPTY_${PN} = "1"

do_compile() {
    cmake -DCMAKE_INSTALL_PREFIX:PATH=${D}/${prefix}/ .
    make
}

do_install() {
    make install
}


FILES_${PN}-dev += " ${datadir}/ECM"

BBCLASSEXTEND = "native nativesdk"
