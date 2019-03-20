DESCRIPTION = "Arch timer test utility"
LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/timertest.git;protocol=ssh;branch=master \
"
SRCREV = "57651e6c7ce8db65a0b85075dd6019912eea7648"

FILES_${PN} = "\
  ${bindir}/timertest \
"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}${bindir}
	install ${S}/timertest ${D}${bindir}/timertest
}
