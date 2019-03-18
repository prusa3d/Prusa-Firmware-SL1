DESCRIPTION = "Arch timer test utility"
LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/timertest.git;protocol=ssh;branch=master \
"
SRCREV = "cb314a11e7b9c034ed767670e17109f462ad6890"

FILES_${PN} = "\
  ${bindir}/timertest \
"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}${bindir}
	install ${S}/timertest ${D}${bindir}/timertest
}
