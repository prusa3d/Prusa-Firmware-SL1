DESCRIPTION = "lazy_import provides a set of functions that load modules, and related attributes, in a lazy fashion."
SECTION = "base"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://README.rst;md5=66354545d563c99dea56ae5e49abb259"

SRC_URI = "https://files.pythonhosted.org/packages/44/2e/5378f9b9cbc893826c2ecb022646c97ece9efbaad351adf89425fff33990/lazy_import-0.2.2.tar.gz"

SRC_URI[md5sum] = "564449119705a15eb288fa94d21b018a"
SRC_URI[sha256sum] = "2149aef8579459407c62cfeccf118527939c9931ace124f355236360644f8a3d"

S = "${WORKDIR}/lazy_import-${PV}"

inherit setuptools
