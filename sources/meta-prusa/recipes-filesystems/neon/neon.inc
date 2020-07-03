DESCRIPTION = "neon is an HTTP and WebDAV client library, with a C interface."
SECTION = "libs"
LICENSE = "GPLv2 & GPLv2"
LIC_FILES_CHKSUM = "file://test/COPYING;md5=8ca43cbc842c2336e835926c2166c28b \
                    file://src/COPYING.LIB;md5=f30a9716ef3762e3467a2f62bf790f0a"

SRC_URI = "git://github.com/notroj/neon.git;protocol=https;nobranch=1"
S = "${WORKDIR}/git"


DEPENDS = "zlib libxml2 expat time gnutls xmlto xmlto-native"

do_configure_prepend() {
    echo ${PV} > ${S}/.version
    (cd ${S}; ./autogen.sh; cd -)
    sed -i \
    's/install-docs: install-man install-html/install-docs: install-nls-no/g' \
    ${S}/Makefile.in
}

inherit autotools binconfig lib_package pkgconfig

EXTRA_OECONF = "\
    --with-ssl=gnutls \
    --with-libxml2 \
    --with-expat \
    --enable-shared \
    --disable-nls \
"
EXTRA_AUTORECONF = " -I ${S}/macros "

