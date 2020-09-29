inherit pypi setuptools3

SUMMARY = "Coroutine-based network library"
HOMEPAGE = "http://www.gevent.org/"
AUTHOR = "Denis Bilenko <denis.bilenko@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4de99aac27b470c29c6c309e0c279b65"

SRC_URI[md5sum] = "9366ee7eebed48003f8a748568ef1f70"
SRC_URI[sha256sum] = "5f6d48051d336561ec08995431ee4d265ac723a64bba99cc58c3eb1a4d4f5c8d"

DEPENDS += "libevent"
DEPENDS += "python3-greenlet"
RDEPENDS_${PN} = "python3-greenlet \
		  python3-mime \
		  python3-pprint \
		 "
# The python-gevent has no autoreconf ability
# and the logic for detecting a cross compile is flawed
# so always force a cross compile
do_configure_append() {
	sed -i -e 's/^cross_compiling=no/cross_compiling=yes/' ${S}/deps/libev/configure
	sed -i -e 's/^cross_compiling=no/cross_compiling=yes/' ${S}/deps/c-ares/configure
}
