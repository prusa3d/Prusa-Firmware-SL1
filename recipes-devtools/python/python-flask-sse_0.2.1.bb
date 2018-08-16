DESCRIPTION = "Simple integration of Flask and WTForms."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e2bbb4a0a20d91650093f296c9e856ab"

SRC_URI = "https://files.pythonhosted.org/packages/4c/25/49b4ee4b2a6d41da593c0d9326738f5c81766638fd1bca4e87a5eec005a1/Flask-SSE-0.2.1.tar.gz"

SRC_URI[md5sum] = "6d4e51d4d511020b6fb2341e5a3ea159"
SRC_URI[sha256sum] = "3723723b78ff67c32f3954a9262eb78bbe324cacd40c6aad5d222ea48f50258d"

PYPI_PACKAGE = "Flask-SSE"

S = "${WORKDIR}/${PYPI_PACKAGE}-${PV}"

RDEPENDS_${PN} = "\
    python-flask \
"

inherit pypi setuptools
