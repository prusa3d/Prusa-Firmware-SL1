SUMMARY = "GammaRay Qt introspection probe"
HOMEPAGE = "https://www.kdab.com/gammaray"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL.txt;md5=7020efa50785f41b29f7229ba36ce169"

inherit cmake_qt5

SRC_URI = "git://github.com/KDAB/GammaRay;branch=master"

SRCREV = "c3966178389527c59f1dd6b0510cba269e4ea8c2"
PV = "2.12.0+git${SRCPV}"

DEPENDS = "elfutils qtdeclarative"

S = "${WORKDIR}/git"

EXTRA_OECMAKE += " -DGAMMARAY_BUILD_UI=OFF"

# The globs specified here should be implied
# TODO: investigate why was it necessary to put it here (it most likely wasn't)
FILES:${PN}-dev += " \
    /usr/lib/cmake/* \
    /usr/mkspecs/modules/* \
"
FILES:${PN}-dbg += " \
    /usr/lib/.debug/* \
    /usr/lib/gammaray/*/*/.debug \
    /usr/lib/gammaray/*/*/styles/.debug \
"
