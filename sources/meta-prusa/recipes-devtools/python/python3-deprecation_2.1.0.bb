inherit pypi setuptools3

SUMMARY = "Deprecation is a library that enables automated deprecations"
DESCRIPTION = "The deprecation library provides a deprecated decorator and a fail_if_not_removed decorator for your tests."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=c55ae0f00b93f6c037d3a12b6e0e7e42"

SRC_URI[md5sum] = "6b79c6572fb241e3cecbbd7d539bb66b"
SRC_URI[sha256sum] = "72b3bde64e5d778694b0cf68178aed03d15e15477116add3fb773e581f9518ff"

PYPI_PACKAGE = "deprecation"

RDEPENDS_${PN} += "python3-packaging"

