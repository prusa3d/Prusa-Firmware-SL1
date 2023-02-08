SUMMARY = "Prusa Link - static web UI for firmware running on a64 board"
HOMEPAGE = "https://github.com/prusa3d/Prusa-Link-Web"
LICENSE = "GPL-3.0 & MIT & CC0-1.0 & Apache-2.0"
SRCREV:pn-${PN} = "c78a9a52cff44e1799649853f39fc236d9e35a21"
SRC_URI = " \
	git://github.com/prusa3d/Prusa-Link-Web.git;protocol=https;branch=old-file-api \
	file://dnssd/http.dnssd \
	file://dnssd/octoprint.dnssd \
	file://nginx/prusa-auth.conf \
    file://nginx/prusa-link.conf.template \
	file://nginx/systemd-override.conf \
	file://prusa-link_enable_nginx_site \
"

LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"
LICENSE:${PN} = "GPL-3.0"

inherit useradd

DEPENDS += " nodejs-native python3"
RDEPENDS:${PN} += "\
    nginx \
    systemd \
    remote-api-link \
    bash \
"

FILES:${PN} += " \
	/srv/http/sl1 \
	/srv/http/m1 \
	/srv/http/sl1s \
	${localstatedir}/sl1fw/old-projects \
	${systemd_unitdir}/dnssd/http.dnssd \
	${systemd_unitdir}/dnssd/octoprint.dnssd \
	${sysconfdir}/nginx/prusa-auth.conf \
	${sysconfdir}/nginx/sites-available/prusa-link.conf.template \
	${sysconfdir}/nginx/http_digest_enabled \
	${systemd_system_unitdir}/nginx.service.d/override.conf \
	${bindir}/prusa-link_enable_nginx_site \
"

S = "${WORKDIR}/git"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = " \
	--system --no-create-home \
	--groups www-data \
	--user-group www"

do_configure() {
	rm package-lock.json
}

do_compile() {
	npm install

	for target in sl1 m1
	do
		npm run build:${target}
		cp -R ${B}/dist ${B}/dist-${target}
	done
}

do_install:append () {
	# mDNS service definition
	install -d ${D}${systemd_unitdir}/dnssd
	install --mode 644 ${WORKDIR}/dnssd/http.dnssd ${D}${systemd_unitdir}/dnssd/
	install --mode 644 ${WORKDIR}/dnssd/octoprint.dnssd ${D}${systemd_unitdir}/dnssd/

	# static web files
	for target in sl1 m1
	do
		install -m 755 -d ${D}/srv/http/${target}/
		cp -R --no-preserve=ownership ${S}/dist-${target}/* ${D}/srv/http/${target}/
		chmod -R 755 ${D}/srv/http/${target}/
		chown www:www-data -R ${D}/srv/http/${target}/
	done
	ln -s sl1 ${D}/srv/http/sl1s

	install -m 755 -d ${D}${localstatedir}/sl1fw/old-projects
	chmod -R 755 ${D}${localstatedir}/sl1fw/old-projects
	chown www:www-data -R ${D}${localstatedir}/sl1fw/old-projects

	# Nginx configuration
	install -d ${D}${sysconfdir}/nginx
	install --mode 755 ${WORKDIR}/nginx/prusa-auth.conf ${D}${sysconfdir}/nginx/
	install -d ${D}${sysconfdir}/nginx/sites-available
	install --mode 644 ${WORKDIR}/nginx/prusa-link.conf.template ${D}${sysconfdir}/nginx/sites-available/
	install -d ${D}${systemd_system_unitdir}/nginx.service.d
	install -m 644 ${WORKDIR}/nginx/systemd-override.conf ${D}${systemd_system_unitdir}/nginx.service.d/override.conf

	# Nginx ExecPre script
	install -d ${D}${bindir}
	install --mode 755 ${WORKDIR}/prusa-link_enable_nginx_site ${D}${bindir}
	touch ${D}${sysconfdir}/nginx/http_digest_enabled
}
