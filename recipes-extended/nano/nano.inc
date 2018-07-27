DESCRIPTION = "GNU nano (Nano's ANOther editor, or \
Not ANOther editor) is an enhanced clone of the \
Pico text editor."
HOMEPAGE = "http://www.nano-editor.org/"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=f27defe1e96c2e1ecd4e0c9be8967949"
SECTION = "console/utils"
DEPENDS = "ncurses"

PV_MAJOR = "${@d.getVar('PV').split('.')[0]}.${@d.getVar('PV').split('.')[1]}"

SRC_URI = "https://nano-editor.org/dist/v${PV_MAJOR}/nano-${PV}.tar.xz"

inherit autotools gettext pkgconfig

EXTRA_OECONF += "\
	--enable-utf8 \
	--enable-tiny \
	--enable-nanorc \
	--enable-color \
	--disable-justify \
	--disable-help \
	--disable-browser \
	--disable-mouse \
	--disable-speller \
	--disable-tabcomp \
	--disable-wordcomp \
	--disable-wrapping \
	--disable-wrapping-as-root \
	--disable-libmagic \
"

