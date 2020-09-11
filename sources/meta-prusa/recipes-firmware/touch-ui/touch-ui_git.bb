SRC_URI = "\
	git://git@gitlab.com/prusa3d/sl1/touch-ui.git;protocol=ssh;name=touch-ui;nobranch=1 \
	git://git@github.com/M4rtinK/qqr.js.git;protocol=ssh;branch=master;name=qrcode-generator;destsuffix=git/3rdparty/qrcode-generator\
	git://git@github.com/martin357/maddy.git;protocol=ssh;branch=master;name=maddy;destsuffix=git/3rdparty/maddy\
	file://touch-ui.service \
	file://touch-ui-config.json \
	file://cz.prusa3d.sl1.Notify1.conf \
"

SRCREV_touch-ui = "f0d1b25102f2da815381e1d68fcc4cd24ba567a3"
SRCREV_qrcode-generator = "bbeeba6e5367f889ac6aa68c0e2219f0479d21a7"
SRCREV_maddy = "51d61b68fed1784d5f587d1969ffe2754563644c"
LICENSE = "GPLv3+" 
LIC_FILES_CHKSUM = "\
	file://COPYING;md5=5b4473596678d62d9d83096273422c8c \
"

inherit cmake_kf5 systemd python3native

DEPENDS += "qtbase qtquickcontrols qtquickcontrols2 qtwebsockets qtsvg qtvirtualkeyboard qtmultimedia nemo-qml-plugin-dbus-qt5 networkmanager-qt prusa-errors-native qtdeclarative-native"

RDEPENDS_${PN} += "\
	bash \
	qtquickcontrols-qmlplugins \
	qtquickcontrols2-qmlplugins \
	qtwebsockets-qmlplugins \
	qtvirtualkeyboard-plugins \
	qtvirtualkeyboard-qmlplugins \
	qtvirtualkeyboard \
	qtmultimedia \
	qtmultimedia-qmlplugins \
	qtmultimedia-plugins \
	qtgraphicaleffects \
	nemo-qml-plugin-dbus-qt5 \
	networkmanager-qt \
	gstreamer1.0-plugins-base \
	gstreamer1.0-plugins-good \
	gstreamer1.0-plugins-bad \
	gstreamer1.0-plugins-ugly \
	gstreamer1.0-libav \
"

FILES_${PN} += "\
	${datadir}/touch-ui-config.json \
	${datadir}/dbus-1/system.d/cz.prusa3d.sl1.Notify1.conf \
"

S="${WORKDIR}/git"


do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/touch-ui.service ${D}${systemd_system_unitdir}/
	
	install -d ${D}/usr/share/
	install --mode 644 ${WORKDIR}/touch-ui-config.json ${D}${datadir}/

	install -d ${D}/usr/share/dbus-1/system.d
	install --mode 644 ${WORKDIR}/cz.prusa3d.sl1.Notify1.conf ${D}${datadir}/dbus-1/system.d/
}

SYSTEMD_SERVICE_${PN} = "touch-ui.service"
