SUMMARY = "Prusa error code definitions"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
	git://github.com/prusa3d/Prusa-Error-Codes.git;protocol=https;branch=master \
"
SRCREV_pn-${PN} = "ebb2110590d170d0f77168210670ac99666a4f84"

DEPENDS += "python3"
RDEPENDS_${PN} += "python3-pyyaml"

S="${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native"
