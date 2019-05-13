SUMMARY = "pydrm is a pure python drm library which can present the framebuffer as a PIL.Image object."

LICENSE = "MIT"

DEPENDS = "python"

inherit setuptools

SRC_URI = "git://github.com/vladamatena/pydrm.git;branch=deploy"
SRCREV_pn-${PN} = "4ad0f06d101a47e36ed738294a5bff6cf0285dfb"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ddbb6e1d20dd3b412325e3a1762a81da"

S = "${WORKDIR}/git"
