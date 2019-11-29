LICENSE = "GPL3"

SRC_URI = "\
	git://git@gitlab.com/prusa3d/sl1/touch-ui.git;protocol=ssh;branch=master;name=sla-client \
	git://git@github.com/M4rtinK/qqr.js.git;protocol=ssh;branch=master;name=qrcode-generator;destsuffix=git/qrcode-generator\
	file://sla-client.service \
	file://sla-client-config.json \
	file://000-install-path.patch \
	file://cz.prusa3d.sl1.notificationsink.conf \
"
#PV = "1.0+git${SRCPV}"
#SRCREV_FORMAT = "sla-client_qrcode-generator"
#SRCREV = "${AUTOREV}"

SRCREV_sla-client = "38315af237c5c724748a01636f23c7cc57b4e0cd"
SRCREV_qrcode-generator = "bbeeba6e5367f889ac6aa68c0e2219f0479d21a7"

LICENSE = "GPLv3+" 
LIC_FILES_CHKSUM = "\
	file://COPYING;md5=5b4473596678d62d9d83096273422c8c \
"
#LICENSE_qrcode-generator = "GPLv3+"
#LIC_FILES_CHKSUM_qrcode-generator = "qrcode-generator/LICENSE.md;md5=a83c38e033331f609f4bbb9b55d68662" 

inherit qmake5 systemd

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
