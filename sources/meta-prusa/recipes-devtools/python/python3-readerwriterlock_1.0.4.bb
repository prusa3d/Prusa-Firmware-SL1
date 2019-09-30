inherit pypi setuptools3

SUMMARY = "A python implementation of the three Reader-Writer problems."
DESCRIPTION = "Not only does it implement the reader-writer problems, it is also compliant with the python lock interface which includes support for timeouts."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=c467a2c3511de79f0336940a63afe591"

SRC_URI += " file://0001-Fix-README-path.patch"
SRC_URI[md5sum] = "5f095b8688ea665cee553b825b186a4a"
SRC_URI[sha256sum] = "cea9a433924cbf50f56bd3487c46ee09251f69ebf44217d5c331c16577b15a9d"

PYPI_PACKAGE = "readerwriterlock"
PYPI_PACKAGE_EXT = "tar.gz"
