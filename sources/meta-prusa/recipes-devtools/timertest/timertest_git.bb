DESCRIPTION = "Arch timer test utility"

LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"
LICENSE = "GPLv3"

SRC_URI = "\
	git://git@gitlab.com/prusa3d/sl1/timertest.git;protocol=ssh;branch=master \
"
SRCREV = "3c9e93a2d52c1ec0b6007965d1b208505bd9fcc1"

FILES:${PN} = "\
  ${bindir}/timertest \
"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}${bindir}
	install ${S}/timertest ${D}${bindir}/timertest
}
