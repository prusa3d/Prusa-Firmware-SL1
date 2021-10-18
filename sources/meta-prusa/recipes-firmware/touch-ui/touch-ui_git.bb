SRC_URI = "\
	gitsm://git@gitlab.com/prusa3d/sl1/touch-ui.git;protocol=ssh;name=touch-ui;nobranch=1 \
	file://touch-ui.service \
	file://cz.prusa3d.sl1.Notify1.conf \
"

SRCREV = "999ae5a1e9981dba9c64feb55d9884c44e28b836"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "\
	file://COPYING;md5=5b4473596678d62d9d83096273422c8c \
"
EXTRA_OECMAKE_append = " -DCMAKE_BUILD_TYPE=Release"

inherit cmake_kf5 systemd python3native

DEPENDS += "qtbase qtquickcontrols qtquickcontrols2 qtsvg qtvirtualkeyboard qtmultimedia networkmanager-qt prusa-errors-native qtdeclarative-native"

RDEPENDS_${PN} += "\
	bash \
	${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'qtwayland', '' ,d)} \
	qtquickcontrols-qmlplugins \
	qtquickcontrols2-qmlplugins \
	qtvirtualkeyboard-plugins \
	qtvirtualkeyboard-qmlplugins \
	qtvirtualkeyboard \
	qtmultimedia \
	qtmultimedia-qmlplugins \
	qtmultimedia-plugins \
	qtgraphicaleffects \
	networkmanager-qt \
	gstreamer1.0-plugins-bad-modplug \
	gstreamer1.0-plugins-base \
	gstreamer1.0-plugins-good \
"

FILES_${PN} += "\
	${datadir}/dbus-1/system.d/cz.prusa3d.sl1.Notify1.conf \
"

S="${WORKDIR}/git"


do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/touch-ui.service ${D}${systemd_system_unitdir}/

	install -d ${D}/usr/share/dbus-1/system.d
	install --mode 644 ${WORKDIR}/cz.prusa3d.sl1.Notify1.conf ${D}${datadir}/dbus-1/system.d/
}

SYSTEMD_SERVICE_${PN} = "touch-ui.service"
