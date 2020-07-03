
DESCRIPTION = "A Linux file system driver that allows you to mount a WebDAV server as a disk drive."
SECTION = "network"
HOMEPAGE = "https://savannah.nongnu.org/projects/davfs2"

LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8f0e2cd40e05189ec81232da84bd6e1a \
                    file://config/COPYING.davfs2;md5=8f0e2cd40e05189ec81232da84bd6e1a"

SRC_URI = "http://download.savannah.nongnu.org/releases/davfs2/davfs2-${PV}.tar.gz"
SRC_URI[md5sum] = "eb9948097dc08664cbc19ad06eeacd97"
SRC_URI[sha256sum] = "417476cdcfd53966b2dcfaf12455b54f315959b488a89255ab4b44586153d801"

inherit autotools useradd gettext xmlcatalog pkgconfig

DEPENDS += "neon"

do_configure_prepend() {
	sed -i \
	's/cd man; po4a po4a.conf; cd ..//g' \
	${S}/bootstrap
	(cd ${S}; ./bootstrap; cd -)
	cp -R ${S}/etc ${B}/etc
}

python do_package_prepend() {
    base_sbindir = d.getVar("base_sbindir")
    if base_sbindir != "/sbin":
        source = d.expand("${D}/sbin")
        with os.scandir(source) as it:
            for entry in it:
                os.remove(entry.path)
        os.rmdir(source)
}

EXTRA_OECONF = " --disable-nls"
EXTRA_AUTORECONF = " -I ${S}/config "
FILES_${PN} = "\
	${base_sbindir}/mount.davfs \
	${base_sbindir}/umount.davfs \
	${sysconfdir}/davfs2/secrets \ 
	${sysconfdir}/davfs2/davfs2.conf \ 
	${sysconfdir}/davfs2/certs/private \
	${datadir}/davfs2/secrets \
	${datadir}/davfs2/davfs2.conf \
"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = " \
    --system \
    --no-create-home \
    --home ${localstatedir}/cache/davfs2 \
    --user-group \
    davfs2 \
"
