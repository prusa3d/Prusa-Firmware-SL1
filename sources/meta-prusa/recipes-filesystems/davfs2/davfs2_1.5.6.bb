
DESCRIPTION = "A Linux file system driver that allows you to mount a WebDAV server as a disk drive."
SECTION = "network"
HOMEPAGE = "https://savannah.nongnu.org/projects/davfs2"

LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8f0e2cd40e05189ec81232da84bd6e1a \
                    file://config/COPYING.davfs2;md5=8f0e2cd40e05189ec81232da84bd6e1a"

SRC_URI = " \
	http://download.savannah.nongnu.org/releases/davfs2/davfs2-${PV}.tar.gz \
	file://0001-Handle-building-with--fno-common-for-gcc-10.patch \
	file://0002-etc.patch \
"
SRC_URI[md5sum] = "eb9948097dc08664cbc19ad06eeacd97"
SRC_URI[sha256sum] = "417476cdcfd53966b2dcfaf12455b54f315959b488a89255ab4b44586153d801"

inherit autotools useradd gettext pkgconfig

DEPENDS:append = " neon"

PACKAGECONFIG ?= ""
PACKAGECONFIG:append:class-target = "\
	${@bb.utils.filter('DISTRO_FEATURES', 'largefile', d)}"
PACKAGECONFIG[largefile] = "--enable-largefile,--disable-largefile,,"

# The dos2unix NLS relies on po4a-native, while po4a recipe is
# provided by meta-perl layer, so make it optional here, you
# need have meta-perl in bblayers.conf before enabling nls in
# PACKAGECONFIG.
PACKAGECONFIG[nls] = ",,po4a-native"
USE_NLS = "${@bb.utils.contains('PACKAGECONFIG', 'nls', 'yes', 'no', d)}"

EXTRA_OECONF:append = " ssbindir=${sbindir} "
EXTRA_AUTORECONF:append = " -I ${S}/config "

CONFFILES:${PN}:append = " \
	${sysconfdir}/davfs2/secrets \ 
	${sysconfdir}/davfs2/davfs2.conf \ 
"
FILES:${PN}:append = " \
	${sysconfdir}/davfs2 \
	${datadir}/davfs2 \
"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = " \
    --system \
    --no-create-home \
    --home ${localstatedir}/cache/davfs2 \
    --user-group \
    davfs2 \
"
