# Package modified
DESCRIPTION = "NetworkManagerQt"
HOMEPAGE = "https://api.kde.org/frameworks/networkmanager-qt/html/index.html"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"
PR = "r0"

DEPENDS = "qtbase networkmanager"

SRC_URI = " \
	git://anongit.kde.org/networkmanager-qt;nobranch=1 \
"

S = "${WORKDIR}/git"
# FILES_${PN} += "${WORKDIR}/build/src/qt_NetworkManagerQt.pri"

do_install_prepend() {
	#sed -i 's+/usr/include/+\$\$QT_MODULE_INCLUDE_BASE/+g' "${WORKDIR}/build/src/qt_NetworkManagerQt.pri"
}

do_install_append() {
	install -d "${D}/usr/lib/mkspecs/modules"
	install --mode 644 ${WORKDIR}/build/src/qt_NetworkManagerQt.pri "${D}/usr/lib/mkspecs/modules"

	sed -i 's+/usr/include/+\$\$QT_MODULE_INCLUDE_BASE/+g' "${D}/usr/lib/mkspecs/modules/qt_NetworkManagerQt.pri"

	sed  -i '/KF5\/NetworkManagerQt/ s/$/ \$\$QT_MODULE_INCLUDE_BASE\/glib-2.0 \$\$QT_MODULE_INCLUDE_BASE\/..\/lib\/glib-2.0\/include  \$\$QT_MODULE_INCLUDE_BASE\/libnm/' "${D}/usr/lib/mkspecs/modules/qt_NetworkManagerQt.pri"
	sed -i 's+/usr/lib+\$\$QT_MODULE_LIB_BASE+g' "${D}/usr/lib/mkspecs/modules/qt_NetworkManagerQt.pri"
}
inherit cmake_kf5

RDEPENDS_${PN} += "networkmanager"

