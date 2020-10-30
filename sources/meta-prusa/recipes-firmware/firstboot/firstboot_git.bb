LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5b4473596678d62d9d83096273422c8c"

SRC_URI = "git://git@gitlab.com/prusa3d/sl1/firstboot.git;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"
SRCREV = "33e47bd7d36cd2c22a1b058ba58f2b166dda9906"
S = "${WORKDIR}/git"

inherit setuptools3 systemd

RDEPENDS_${PN} += "python3-pydbus"
RDEPENDS_${PN} += "python3-core"
SYSTEMD_SERVICE_${PN} = "\
	firstboot.service \
	storetime.service \
"

do_install_append() {
	rmdir --ignore-fail-on-non-empty ${D}${datadir}
}
