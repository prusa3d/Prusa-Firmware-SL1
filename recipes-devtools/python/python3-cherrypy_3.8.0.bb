inherit pypi setuptools3

DESCRIPTION = "CherryPy HTTP framework"
HOMEPAGE = "https://cherrypy.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://cherrypy/LICENSE.txt;md5=a476d86a3f85c89411ecaad012eed1e3"

SRC_URI[md5sum] = "542b96b2cd825e8120e8cd822bc18f4b"
SRC_URI[sha256sum] = "ffcdb43667d4098247efaf8c82dd36d3dd4f8e5dc768ef5e90b480899e523bea"

RDEPENDS_${PN} += "python3-mmap"

PYPI_PACKAGE = "CherryPy"
PYPI_PACKAGE_EXT = "tar.gz"

FILES_${PN} += "/usr/lib/* /usr/share/cherrypy"
