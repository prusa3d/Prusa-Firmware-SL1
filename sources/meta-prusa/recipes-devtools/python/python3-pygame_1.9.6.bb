DESCRIPTION = "Python libSDL Bindings"
SECTION = "devel/python"
HOMEPAGE = "http://www.pygame.org"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://docs/LGPL;md5=7fbc338309ac38fefcd64b04bb903e34"
DEPENDS = "python3-numpy virtual/libsdl libsdl-image libsdl-mixer libsdl-ttf freetype"
SRCNAME = "pygame"
PR = "ml5"

SRC_URI = " \
	http://www.pygame.org/ftp/${SRCNAME}-${PV}.tar.gz;name=archive \
	file://0001-sdl-config.patch \
"
SRC_URI[archive.md5sum] = "36f8817874f9e63acdf12914340b60e9"
SRC_URI[archive.sha256sum] = "301c6428c0880ecd4a9e3951b80e539c33863b6ff356a443db1758de4f297957"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit pkgconfig setuptools3

do_configure:prepend() {
	cd ${S}
	LOCALBASE="${RECIPE_SYSROOT}${prefix}" ${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN} buildconfig/config.py -auto

	sed -e "s:^scrap :#&:; s:^pypm :#&:" -i ${S}/Setup || die "sed failed"
} 
