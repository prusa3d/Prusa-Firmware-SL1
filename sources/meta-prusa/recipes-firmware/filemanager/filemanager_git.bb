SUMMARY = "Prusa SLA File Manager"
HOMEPAGE = "https://gitlab.com/prusa3d/sl1/filemanager.git"
LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "\
	git://gitlab.com/prusa3d/sl1/filemanager.git;protocol=https;nobranch=1 \
	file://filemanager.service \
"
SRCREV:pn-${PN} = "213956ad3cc6e217b0fb2750f69992c447e03184"

S = "${WORKDIR}/git"

inherit setuptools3 systemd

FILES:${PN} += "${datadir}/dbus-1/system.d"

RDEPENDS:${PN} += " \
	python3 \
	python3-watchdog \
	prusa-errors \
	slafw \
"

do_install:append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/filemanager.service ${D}${systemd_system_unitdir}/
}

SYSTEMD_SERVICE:${PN} = "filemanager.service"

