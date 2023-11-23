# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

SUMMARY = "Python library for extraction of metadata from g-code files"
HOMEPAGE = "https://github.com/prusa3d/gcode-metadata"
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "LGPL-2.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d9ff93b9f837b3a093c3f74e7270412d"

SRC_URI = "git://github.com/prusa3d/gcode-metadata.git;protocol=https"

# Modify these as desired
PV = "0.2.0dev+git${SRCPV}"
SRCREV = "47e2c1e8bb01b5db0add407286d4aca2ca19d8b5"

S = "${WORKDIR}/git"

inherit setuptools3

# WARNING: the following rdepends are determined through basic analysis of the
# python sources, and might not be 100% accurate.
RDEPENDS:${PN} += "python3-compression python3-core python3-json python3-logging python3-netclient python3-mypy-extensions"
