SUMMARY = "Prusa error code definitions"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
	git://github.com/prusa3d/Prusa-Error-Codes.git;protocol=https;branch=master \
"
SRCREV:pn-${PN} = "9bb23e38d0c15f3e650c87d0ef149c200671b0ce"

DEPENDS += "python3"
RDEPENDS:${PN} += "python3-pyyaml"

S="${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native"
