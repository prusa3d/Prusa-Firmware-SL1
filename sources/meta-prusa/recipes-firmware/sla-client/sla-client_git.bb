LICENSE = "GPL3"

SRC_URI = "\
	git://git@gitlab.com/prusa3d/sl1/touch-ui.git;protocol=ssh;branch=1.4;name=sla-client \
	git://git@github.com/M4rtinK/qqr.js.git;protocol=ssh;branch=master;name=qrcode-generator;destsuffix=git/3rdparty/qrcode-generator\
	git://git@github.com/martin357/maddy.git;protocol=ssh;branch=master;name=maddy;destsuffix=git/3rdparty/maddy\
	file://sla-client.service \
	file://sla-client-config.json \
	file://cz.prusa3d.sl1.notificationsink.conf \
"

SRCREV_sla-client = "e5da805f6cb274c5c3cadfed76603afe42eae4fb"
SRCREV_qrcode-generator = "bbeeba6e5367f889ac6aa68c0e2219f0479d21a7"
SRCREV_maddy = "51d61b68fed1784d5f587d1969ffe2754563644c"
LICENSE = "GPLv3+" 
LIC_FILES_CHKSUM = "\
	file://COPYING;md5=5b4473596678d62d9d83096273422c8c \
"

inherit cmake_kf5 systemd

DEPENDS += "qtbase qtquickcontrols qtquickcontrols2 qtwebsockets qtsvg qtvirtualkeyboard qtmultimedia nemo-qml-plugin-dbus-qt5 networkmanager-qt"

RDEPENDS_${PN} += "\
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
	/usr/share/sla-client-config.json \
	/usr/share/dbus-1/system.d/cz.prusa3d.sl1.notificationsink.conf \
"

S="${WORKDIR}/git"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-client.service ${D}${systemd_system_unitdir}/
	
	install -d ${D}/usr/share/
	install --mode 644 ${WORKDIR}/sla-client-config.json ${D}/usr/share/

	install -d ${D}/usr/share/dbus-1/system.d
	install --mode 644 ${WORKDIR}/cz.prusa3d.sl1.notificationsink.conf ${D}/usr/share/dbus-1/system.d/
}

SYSTEMD_SERVICE_${PN} = "sla-client.service"
