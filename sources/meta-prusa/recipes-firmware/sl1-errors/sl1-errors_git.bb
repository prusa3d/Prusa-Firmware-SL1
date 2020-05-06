SUMMARY = "SL1 errors code definitions"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
	git://git@gitlab.com/prusa3d/sl1/sl1-errors.git;protocol=ssh;branch=master \
"
SRCREV_pn-${PN} = "45b1226c8abd9cb491f8e818684dba630de0db74"

DEPENDS += "python3"

S="${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native"
