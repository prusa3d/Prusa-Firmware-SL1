inherit pypi setuptools3

SUMMARY = "Linux sysfs gpio access"
DESCRIPTION = "python gpio module for linux using the sysfs file access (/sys/class/gpio). Mimics similar Raspberry Pi IO libraries"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=de0bbb14174882150bac3636a88f51bd"

SRC_URI[md5sum] = "e9f30a756fa92961383e2ee01395db5d"
SRC_URI[sha256sum] = "0b8db65f554ad7e6733910ead6560f9800d179a73a3e4e3413e619fc334f8c7b"

RDEPENDS_${PN} = "python3-debugger"

PYPI_PACKAGE = "gpio"
PYPI_PACKAGE_EXT = "tar.gz"
