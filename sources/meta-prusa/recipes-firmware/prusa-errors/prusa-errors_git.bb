SUMMARY = "Prusa error code definitions"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
	git://github.com/prusa3d/Prusa-Error-Codes.git;protocol=https;branch=master \
"
SRCREV_pn-${PN} = "cbf735bb1160c32c4923cf403a11cd646591ee8f"

DEPENDS += "python3"

S="${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native"
