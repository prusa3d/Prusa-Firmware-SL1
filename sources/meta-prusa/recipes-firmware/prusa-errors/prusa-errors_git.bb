SUMMARY = "Prusa error code definitions"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
	git://github.com/prusa3d/Prusa-Error-Codes.git;protocol=https;branch=SL1SW-2110-odstranit-api-klic-z-yocto-a-touchui \
"
SRCREV:pn-${PN} = "2ca7dc802598ee31d96794a9bbe0fd905aa2a10c"

DEPENDS += "python3"
RDEPENDS:${PN} += "python3-pyyaml"

S="${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native"
