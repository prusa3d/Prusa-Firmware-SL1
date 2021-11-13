LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=937511e42dab6bf5fc0786f06fd377a8"

SRC_URI = "https://github.com/fish-shell/fish-shell/releases/download/${PV}/${P}.tar.gz"
SRC_URI[md5sum] = "2e9f12a250dccb9ab0529ae6f77710a0"
SRC_URI[sha256sum] = "d5b927203b5ca95da16f514969e2a91a537b2f75bec9b21a584c4cd1c7aa74ed"

DEPENDS = "ncurses libpcre2"
inherit cmake

EXTRE_OECMAKE:append = " -DCURSES_NEED_NCURSES=ON -DBUILD_DOCS=OFF -DWITH_GETTEXT=OFF"

do_configure:prepend() {
	sed -i 's#${TEST_INSTALL_DIR}/${CMAKE_INSTALL_PREFIX}#${TEST_INSTALL_DIR}#' \
		${S}/cmake/Tests.cmake
}
