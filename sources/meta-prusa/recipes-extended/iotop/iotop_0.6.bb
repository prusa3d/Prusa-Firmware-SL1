SUMMARY = "Simple top-like I/O monitor"
DESCRIPTION = "iotop does for I/O usage what top(1) does for CPU usage. \
    It watches I/O usage information output by the Linux kernel and displays \
    a table of current I/O usage by processes on the system."
HOMEPAGE = "http://guichaz.free.fr/iotop/"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4325afd396febcb659c36b49533135d4"

SRC_URI = " \
	http://guichaz.free.fr/iotop/files/${BP}.tar.bz2 \
	file://iotop-0.6-setup.py3.patch \
"
SRC_URI[md5sum] = "5ef9456b26d7694abf3101a72e1e0d1d"
SRC_URI[sha256sum] = "3adea2a24eda49bbbaeb4e6ed2042355b441dbd7161e883067a02bfc8dcef75b"

UPSTREAM_CHECK_URI = "http://repo.or.cz/iotop.git/tags"
UPSTREAM_CHECK_REGEX = "iotop-(?P<pver>\d+(\.\d+)+)"

inherit distutils3

do_install_append() {
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/site.pyo || true
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/site.py  || true
    
    # TODO: Fix setup, for some reasone python3-native is used to execute the script
    sed -i 's%#!/usr/bin/python3-native/python3%#!/usr/bin/python3%' ${D}/usr/sbin/iotop
}

RDEPENDS_${PN} = "python3 python3-curses python3-codecs python3-ctypes python3-pprint python3-shell"
