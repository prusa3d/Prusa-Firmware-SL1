SUMMARY = "web ui - static web files firmware part running on a64 board" 
HOMEPAGE = "https://github.com/prusa3d/Prusa-Connect-Local"
LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRCREV_pn-${PN} = "216dcb119108ca4437c0c4cd5c3affe8a543d36f"
SRC_URI = " \
	git://git@github.com/prusa3d/Prusa-Connect-Local.git;protocol=ssh;branch=master \
	file://avahi/octoprint.service \
	file://nginx/prusa-auth.conf \
	file://nginx/error_401.txt \
	file://nginx/sl1fw \
"

inherit useradd

DEPENDS += " nodejs nodejs-native python3"
RDEPENDS_${PN} += "\
	nginx \
	avahi-daemon \
	avahi-restarter \
	sl1fw-api \
"
FILES_${PN} += " \
	/srv/http/intranet \
	${sysconfdir}/nginx/prusa-auth.conf \
	${sysconfdir}/nginx/sites-available/sl1fw\
	${sysconfdir}/nginx/sites-enabled/sl1fw\
	${sysconfdir}/avahi/services/octoprint.service \
"
S = "${WORKDIR}/git"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = " \
	--system --no-create-home \
	--groups www-data \
	--user-group www"

do_compile() {
	npm install
	node node_modules/webpack/bin/webpack.js --config ./webpack.config.js --mode production --env.apiKey=developer --env.printer=sl1
}

do_install_append () {

    # Avahi service definition
	install -d ${D}${sysconfdir}/avahi/services
	install --mode 644 ${WORKDIR}/avahi/octoprint.service ${D}${sysconfdir}/avahi/services/octoprint.service

	# static web files
	install -m 755 -d ${D}/srv/http/intranet/
	cp -R --no-preserve=ownership ${S}/dist/* ${D}/srv/http/intranet/
    install --mode 755 ${WORKDIR}/nginx/error_401.txt ${D}/srv/http/intranet/error_401.txt
	chmod -R 755 ${D}/srv/http/intranet/
	chown www:www-data -R ${D}/srv/http/intranet/

	# Nginx htdigest configuration
	install -d ${D}${sysconfdir}/nginx
	install --mode 755 ${WORKDIR}/nginx/prusa-auth.conf ${D}${sysconfdir}/nginx/

	# Enable nginx site
	install --mode 755 -d ${D}${sysconfdir}/nginx/sites-available
	install --mode 755 -d ${D}${sysconfdir}/nginx/sites-enabled
	install --mode 644 ${WORKDIR}/nginx/sl1fw ${D}${sysconfdir}/nginx/sites-available/sl1fw
	ln -s ${sysconfdir}/nginx/sites-available/sl1fw ${D}${sysconfdir}/nginx/sites-enabled/sl1fw
}
