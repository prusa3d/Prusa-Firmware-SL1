SUMMARY = "Extra CMake Modules"
HOMEPAGE = "https://api.kde.org/frameworks/extra-cmake-modules/html/index.html"
LICENSE = "BSD-2-Clause & BSD-3-Clause & MIT"
LIC_FILES_CHKSUM = " \
	file://LICENSES/BSD-2-Clause.txt;md5=63d6ee386b8aaba70b1bf15a79ca50f2 \
	file://LICENSES/BSD-3-Clause.txt;md5=954f4d71a37096249f837652a7f586c0 \
	file://LICENSES/MIT.txt;md5=38aa75cf4c4c87f018227d5ec9638d75 \
"
PR = "r0"

inherit cmake_qt5

SRC_URI = "git://invent.kde.org/frameworks/extra-cmake-modules.git;protocol=https;nobranch=1 \
	   file://0001-avoid-docs-subdirectory.patch"
S = "${WORKDIR}/git"

CLEANBROKEN = "1"

ALLOW_EMPTY:${PN} = "1"

FILES:${PN}-dev = " ${datadir}/ECM"

BBCLASSEXTEND = "native nativesdk"
