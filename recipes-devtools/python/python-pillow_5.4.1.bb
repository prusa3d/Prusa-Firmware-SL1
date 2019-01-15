inherit pypi setuptools

SUMMARY = "Pillow is the friendly PIL (where PIL is the Python Imaging Library)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c6379001ecb47e2a0420c40177fc1125"

# SRC_URI = "https://github.com/python-pillow/Pillow/archive/${PV}.tar.gz;downloadfilename=Pillow-${PV}.tar.gz"
#
SRC_URI += "\
    file://0001-build-always-disable-platform-guessing.patch \
"

SRC_URI[md5sum] = "f7d0ce066fc7ea7a685739227887aeaf"
SRC_URI[sha256sum] = "5233664eadfa342c639b9b9977190d64ad7aca4edc51a966394d7e08e7f38a9f"

PYPI_PACKAGE = "Pillow"
UPSTREAM_CHECK_URI = "https://pypi.python.org/pypi/Pillow"

# S = "${WORKDIR}/Pillow-${PV}"

DEPENDS += "lcms freetype libpng jpeg zlib tiff libwebp"
RDEPENDS_${PN}_class-target += " \
	${PYTHON_PN}-dateutil \
	${PYTHON_PN}-pytz \
	${PYTHON_PN}-six \
        ${PYTHON_PN}-jinja2 \
	${PYTHON_PN}-markupsafe \
	${PYTHON_PN}-docutils \
	${PYTHON_PN}-requests \
	${PYTHON_PN}-pyflakes \
	${PYTHON_PN}-pep8 \
"

# this is in the upstream docs but no configure exists  :(
EXTRA_OECONF = " --disable-platform-guessing --disable-tcl --disable-tk \
                 --disable-webp --disable-webpmux --disable-jpeg2000 \
                 --disable-imagequant"

CFLAGS_append = " -I${STAGING_INCDIR}"
LDFLAGS_append = " -L${STAGING_LIBDIR}"

do_compile_prepend() {
    export LDFLAGS="$LDFLAGS -L${STAGING_LIBDIR}"
    export CFLAGS="$CFLAGS -I${STAGING_INCDIR}"
}

# do_compile_prepend() {
#        sed -i -e s:/usr/local/lib:${STAGING_LIBDIR}:g \
#               -e s:/usr/local/lib64:${STAGING_LIBDIR}:g \
#               -e s:/usr/lib:${STAGING_LIBDIR}:g \
#               -e s:/usr/lib64:${STAGING_LIBDIR}:g \
#               -e s:/lib:${STAGING_BASELIBDIR}:g \
#               -e s:/lib64:${STAGING_BASELIBDIR}:g \
#               -e s:/usr/include:${STAGING_INCDIR}:g \
#               ${S}/setup.py
#}


BBCLASSEXTEND = "native"
