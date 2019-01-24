DESCRIPTION = "Python libSDL Bindings"
SECTION = "devel/python"
HOMEPAGE = "http://www.pygame.org"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LGPL;md5=7fbc338309ac38fefcd64b04bb903e34"
DEPENDS = "virtual/libsdl libsdl-image libsdl-mixer libsdl-ttf python-numeric freetype"
RDEPENDS_${PN} = "python-numeric "
SRCNAME = "pygame"
PR = "ml5"

SRC_URI = "http://www.pygame.org/ftp/${SRCNAME}-${PV}.tar.gz;name=archive"
SRC_URI[archive.md5sum] = "35123425da093da331a89ec0dcbd1ac4"
SRC_URI[archive.sha256sum] = "700d1781c999af25d11bfd1f3e158ebb660f72ebccb2040ecafe5069d0b2c0b6"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools

do_configure_prepend() {
	# Drop options to replace them later and drom pypm as we don't have portmidi/porttime
	sed '/^FREETYPE = /d; /^SDL =/d; /^SMPEG =/d' Setup.in > Setup.tmp
	sed '/^movie src/d; /^pypm src/d; /^scrap src/d; /^mixer src/d; /^mixer_music/d' Setup.tmp > Setup
	SDL="`pkg-config sdl --cflags --libs`"; echo "SDL=$SDL" >> Setup
	FREETYPE="`pkg-config freetype2 --cflags --libs`"; echo "FREETYPE=$FREETYPE" >> Setup
} 
