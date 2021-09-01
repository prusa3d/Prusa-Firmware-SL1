SUMMARY = "web ui - static web files firmware part running on a64 board"
HOMEPAGE = "https://github.com/prusa3d/Prusa-Connect-Local"
LICENSE = "GPL-3.0 & MIT & CC0-1.0 & Apache-2.0"
SRCREV_pn-${PN} = "ff13211019d2cc6c9a13f42fbba872f332836552"
SRC_URI = " \
	git://git@github.com/prusa3d/Prusa-Connect-Local.git;protocol=ssh;nobranch=1 \
	file://dnssd/http.dnssd \
	file://dnssd/octoprint.dnssd \
	file://nginx/prusa-auth.conf \
	file://nginx/sl1fw \
	file://nginx/sl1fw_http_digest \
"

LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"
LICENSE_${PN} = "GPL-3.0"

inherit useradd

DEPENDS += " nodejs-native python3"
RDEPENDS_${PN} += "\
	nginx \
	systemd \
	sl1fw-api \
"
FILES_${PN} += " \
	/srv/http/intranet \
	${localstatedir}/sl1fw/old-projects \
	${sysconfdir}/nginx/prusa-auth.conf \
	${sysconfdir}/nginx/sites-available/sl1fw\
	${sysconfdir}/nginx/sites-available/sl1fw_http_digest\
	${sysconfdir}/nginx/sites-enabled/sl1fw\
	${systemd_unitdir}/dnssd/http.dnssd \
	${systemd_unitdir}/dnssd/octoprint.dnssd \
"
S = "${WORKDIR}/git"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = " \
	--system --no-create-home \
	--groups www-data \
	--user-group www"

do_compile() {
	npm install
	npm run build:sl1
	node node_modules/webpack/bin/webpack.js --config ./webpack.config.js --mode production --env PRINTER=SL1
}

do_install_append () {

	# mDNS service definition
	install -d ${D}${systemd_unitdir}/dnssd
	install --mode 644 ${WORKDIR}/dnssd/http.dnssd ${D}${systemd_unitdir}/dnssd/
	install --mode 644 ${WORKDIR}/dnssd/octoprint.dnssd ${D}${systemd_unitdir}/dnssd/

	# static web files
	install -m 755 -d ${D}/srv/http/intranet/
	cp -R --no-preserve=ownership ${S}/dist/* ${D}/srv/http/intranet/
	chmod -R 755 ${D}/srv/http/intranet/
	chown www:www-data -R ${D}/srv/http/intranet/
	install -m 755 -d ${D}${localstatedir}/sl1fw/old-projects
	chmod -R 755 ${D}${localstatedir}/sl1fw/old-projects
	chown www:www-data -R ${D}${localstatedir}/sl1fw/old-projects

	# Nginx htdigest configuration
	install -d ${D}${sysconfdir}/nginx
	install --mode 755 ${WORKDIR}/nginx/prusa-auth.conf ${D}${sysconfdir}/nginx/

	# Enable nginx site
	install --mode 755 -d ${D}${sysconfdir}/nginx/sites-available
	install --mode 755 -d ${D}${sysconfdir}/nginx/sites-enabled
	install --mode 644 ${WORKDIR}/nginx/sl1fw ${D}${sysconfdir}/nginx/sites-available/sl1fw
	install --mode 644 ${WORKDIR}/nginx/sl1fw_http_digest ${D}${sysconfdir}/nginx/sites-available/sl1fw_http_digest
	ln -s ${sysconfdir}/nginx/sites-available/sl1fw_http_digest ${D}${sysconfdir}/nginx/sites-enabled/sl1fw
}
