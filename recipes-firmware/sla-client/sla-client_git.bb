LICENSE = "CLOSED"

SRC_URI = "\
	git://git@gitlab.webdev.prusa3d.com:22443/martin.kopecky/new-sla-client-application.git;protocol=ssh;branch=master \
	git://git@github.com/M4rtinK/qqr.js.git;protocol=ssh;branch=master;name=qrcode-generator;destsuffix=git/qrcode-generator\
	file://sla-client.service \
	file://sla-client-config.json \
	file://000-install-path.patch \
"
SRCREV = "2bd0b66e177fe37e3d4bcfd3c4b30cc4b8bcffff"
SRCREV_qrcode-generator = "bbeeba6e5367f889ac6aa68c0e2219f0479d21a7"

inherit qmake5 systemd

DEPENDS += "qtbase qtquickcontrols qtquickcontrols2 qtwebsockets qtsvg qtvirtualkeyboard"
# qtquickcontrols2 qttools-plugins

RDEPENDS_${PN} += "qtquickcontrols-qmlplugins qtquickcontrols2-qmlplugins qtwebsockets-qmlplugins qtvirtualkeyboard-plugins qtvirtualkeyboard-qmlplugins qtvirtualkeyboard"

FILES_${PN} += "\
	/usr/share/sla-client-config.json \
"

S="${WORKDIR}/git"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/sla-client.service ${D}${systemd_system_unitdir}/
	
	install -d ${D}/usr/share/
	install --mode 644 ${WORKDIR}/sla-client-config.json ${D}/usr/share/
}

SYSTEMD_SERVICE_${PN} = "sla-client.service"
