SUMMARY = "Automounter for removable media"

LICENSE = "MIT"

DEPENDS = "asciidoc-native gettext-native"
RDEPENDS_${PN} = "udisks2 python-pyyaml pyxdg python-pygobject python-docopt python-logging"

inherit setuptools systemd

SRC_URI = " \
	https://files.pythonhosted.org/packages/e0/5f/ab915ec251dfdc27f852204196976cb14e74cfb04a5dd58b7e7678592def/udiskie-1.7.5.tar.gz \
	file://udiskie.service \
	file://udiskie.yaml \
"
SRC_URI[md5sum] = "ddfb4225062577c7f11461308dd6b82b"
SRC_URI[sha256sum] = "d2b80b014a5f22037312f1be04e74a0621fd24d2e769c73eb622959f9f003466"
LIC_FILES_CHKSUM = "file://COPYING;md5=564cde358e292596069e5168f3a773f1"

FILES_${PN} += " \
	/usr/share/icons \
	/usr/share/zsh \
"

do_install_append () {
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/udiskie.service ${D}${systemd_system_unitdir}/
	
	install -d ${D}${sysconfdir}
	install --mode 644 ${WORKDIR}/udiskie.yaml ${D}${sysconfdir}/udiskie.yaml
}

SYSTEMD_SERVICE_${PN} = "udiskie.service"
