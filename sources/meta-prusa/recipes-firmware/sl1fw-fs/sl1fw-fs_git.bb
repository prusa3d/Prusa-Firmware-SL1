SUMMARY = "Prusa SL1 File Manager"
HOMEPAGE = "https://gitlab.com/prusa3d/sl1/filemanager.git"
LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "\
	git://git@gitlab.com/prusa3d/sl1/filemanager.git;protocol=ssh;nobranch=1 \
	file://sl1fw_fs.service \
"
SRCREV:pn-${PN} = "8607c45bce6e3c2654740b3770d6057703871bc0"

S = "${WORKDIR}/git"

inherit setuptools3 systemd

FILES:${PN} += "${datadir}/dbus-1/system.d"

RDEPENDS:${PN} += " \
	python3 \
	python3-watchdog \
	prusa-errors \
	sl1fw \
"

do_install:append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sl1fw_fs.service ${D}${systemd_system_unitdir}/
}

SYSTEMD_SERVICE:${PN} = "sl1fw_fs.service"

