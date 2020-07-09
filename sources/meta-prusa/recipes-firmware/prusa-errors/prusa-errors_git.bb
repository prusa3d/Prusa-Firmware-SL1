SUMMARY = "Prusa error code definitions"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
	git://github.com/prusa3d/Prusa-Error-Codes.git;protocol=https;branch=master \
"
SRCREV_pn-${PN} = "8f987d831b6d516e33467c3a0021d225a1f97b54"

DEPENDS += "python3"

S="${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native"
