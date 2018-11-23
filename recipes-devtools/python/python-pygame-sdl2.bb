DESCRIPTION = "Python libSDL2 Bindings"
SECTION = "devel/python"
HOMEPAGE = "http://www.pygame.org"
LICENSE = "Zlib & LGPLv2.1"
LIC_FILES_CHKSUM = "\
file://COPYING.ZLIB;md5=80a8c874b5a9364c1accb2de082735c4 \
file://COPYING.LGPL21;md5=23c2a5e0106b99d75238986559bb5fc6 \
"

DEPENDS = "virtual/libsdl2 libsdl2-image libsdl2-mixer python-cython-native"
SRCNAME = "pygame_sdl2"

SRC_URI = "\
git://github.com/renpy/pygame_sdl2.git \
file://disable-ttf.patch \
"
SRCREV = "f2b6ff38d5793a1904970daa192915da377bb315"
S = "${WORKDIR}/git"

inherit setuptools

