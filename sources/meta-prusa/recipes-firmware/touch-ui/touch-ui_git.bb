SRC_URI = "\
	gitsm://gitlab.com/prusa3d/sl1/touch-ui.git;protocol=https;name=touch-ui;branch=1.7 \
	file://touch-ui.service \
	file://cz.prusa3d.sl1.Notify1.conf \
"

SRCREV = "bc3dfee68aad37bd29d75fcd33d72a05c73de599"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "\
	file://COPYING;md5=5b4473596678d62d9d83096273422c8c \
"
EXTRA_OECMAKE:append = " -DCMAKE_BUILD_TYPE=Release"

inherit cmake_kf5 systemd python3native

DEPENDS += "qtbase qtquickcontrols qtquickcontrols2 qtsvg qttools qtvirtualkeyboard qtmultimedia networkmanager-qt prusa-errors-native qtdeclarative-native"

RDEPENDS:${PN} += "\
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
	sla-multimedia \
"

FILES:${PN} += "\
	${datadir}/dbus-1/system.d/cz.prusa3d.sl1.Notify1.conf \
"

S="${WORKDIR}/git"


do_install:append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/touch-ui.service ${D}${systemd_system_unitdir}/

	install -d ${D}/usr/share/dbus-1/system.d
	install --mode 644 ${WORKDIR}/cz.prusa3d.sl1.Notify1.conf ${D}${datadir}/dbus-1/system.d/
}

SYSTEMD_SERVICE:${PN} = "touch-ui.service"
