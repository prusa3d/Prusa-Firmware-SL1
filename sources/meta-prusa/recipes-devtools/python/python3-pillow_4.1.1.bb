SUMMARY = "Python library that provides an easy interface to read and write a wide range of image data, including animated images, video, volumetric data, and scientific formats."
SECTION = "devel/python"
LICENSE = "PIL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fbbe34c5344f5c542baf8ef7621305d0"

inherit setuptools3 pkgconfig

SRC_URI = " \
    https://github.com/python-pillow/Pillow/archive/${PV}.tar.gz \
    file://0001-build-always-disable-platform-guessing.patch \
"

SRC_URI[md5sum] = "4889a8297f4068e339cbe8878db1834c"
SRC_URI[sha256sum] = "606eca4a0801461e34d3bb4eb94df28fab5b3f6702eac8d1840d2da6a6d5d970"

S = "${WORKDIR}/Pillow-${PV}"

DEPENDS += "python3 libjpeg-turbo zlib tiff freetype libpng jpeg"

CFLAGS_append = " -I${STAGING_INCDIR}"
LDFLAGS_append = " -L${STAGING_LIBDIR}"

do_compile_prepend() {
    export LDFLAGS="$LDFLAGS -L${STAGING_LIBDIR}"
    export CFLAGS="$CFLAGS -I${STAGING_INCDIR}"
}
