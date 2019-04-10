inherit pypi setuptools3

DESCRIPTION = "bitstring is a pure Python module designed to help make the creation and analysis of binary data as simple and natural as possible."
HOMEPAGE = "https://github.com/scott-griffiths/bitstring"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4680edc365ce7b05888336af92064330"

SRC_URI[md5sum] = "70689a282f66625d0c7c3579a13e66db"
SRC_URI[sha256sum] = "c163a86fcef377c314690051885d86b47419e3e1770990c212e16723c1c08faa"

RDEPENDS_${PN} += "python3-mmap"

PYPI_PACKAGE_EXT = "zip"
