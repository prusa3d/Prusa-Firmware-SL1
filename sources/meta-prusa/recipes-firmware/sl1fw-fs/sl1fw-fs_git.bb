SUMMARY = "Prusa SL1 File Manager"
HOMEPAGE = "https://gitlab.com/prusa3d/sl1/filemanager.git"
LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "git://git@gitlab.com/prusa3d/sl1/filemanager.git;protocol=ssh;branch=master"
SRCREV_pn-${PN} = "3e22e40c814d5db86d0aa005e31365fa9cdd1635"

S = "${WORKDIR}/git"

inherit setuptools3 systemd

FILES_${PN} += "\
	${libdir}/systemd/system/sl1fw_fs.service \
	${datadir}/dbus-1/system.d \
"

RDEPENDS_${PN} += " \
	python3 \
	python3-pyinotify \
	prusa-errors \
	sl1fw \
"

SYSTEMD_SERVICE_${PN} = "sl1fw_fs.service"

