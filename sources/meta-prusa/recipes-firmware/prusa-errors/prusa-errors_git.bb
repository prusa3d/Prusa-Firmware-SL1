SUMMARY = "Prusa error code definitions"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
	git://github.com/prusa3d/Prusa-Error-Codes.git;protocol=https;branch=master \
"
SRCREV_pn-${PN} = "d3f7a196f1b0afff1fe194da392cd28a4a1a9c79"

DEPENDS += "python3"

S="${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native"
