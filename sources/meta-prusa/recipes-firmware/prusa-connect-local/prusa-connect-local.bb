SUMMARY = "web ui - static web files firmware part running on a64 board" 
HOMEPAGE = "https://github.com/prusa3d/Prusa-Connect-Local"
LICENSE = "GPL-3.0 & MIT & CC0-1.0 & Apache-2.0"
SRCREV_pn-${PN} = "307ea7c40c1d1cd5f05003fa427f5df4bac56e8a"
SRC_URI = " \
	git://git@github.com/prusa3d/Prusa-Connect-Local.git;protocol=ssh;branch=master \
	https://raw.githubusercontent.com/jgthms/bulma/master/LICENSE;md5sum=814d77c8b54f22875dfdb6f42417ad45;downloadfilename=LICENSE-bulma \
	https://raw.githubusercontent.com/i18next/i18next/master/LICENSE;md5sum=16e7b81632cf4b844087a62a1242bec5;downloadfilename=LICENSE-i18next \
	https://raw.githubusercontent.com/i18next/i18next-browser-languageDetector/master/LICENSE;md5sum=f4112c384a38707998b5a2af4cfc4154;downloadfilename=LICENSE-i18next-browser-languageDetector \
	https://raw.githubusercontent.com/i18next/i18next-xhr-backend/master/LICENSE;md5sum=f4112c384a38707998b5a2af4cfc4154;downloadfilename=LICENSE-i18next-xhr-backend \
	https://raw.githubusercontent.com/preactjs/preact/master/LICENSE;md5sum=11497b3621048be9724baca2ec0d85ee;downloadfilename=LICENSE-preact \
	https://raw.githubusercontent.com/preactjs/preact-router/master/LICENSE;md5sum=8318b79a651efc0165e52d30e14babde;downloadfilename=LICENSE-preact-router \
	https://raw.githubusercontent.com/i18next/react-i18next/master/LICENSE;md5sum=f4112c384a38707998b5a2af4cfc4154;downloadfilename=LICENSE-react-i18next \
	https://raw.githubusercontent.com/DefinitelyTyped/DefinitelyTyped/master/LICENSE;md5sum=dbd0ec53724558452cfcfbb506e27104;downloadfilename=LICENSE-types-react \
	https://raw.githubusercontent.com/postcss/autoprefixer/master/LICENSE;md5sum=e0ef868fdaaba6859dcbab082c20439b;downloadfilename=LICENSE-autoprefixer \
	https://raw.githubusercontent.com/johnagan/clean-webpack-plugin/master/LICENSE;md5sum=0adff6b3db29616057454babc15a4179;downloadfilename=LICENSE-clean-webpack-plugin \
	https://raw.githubusercontent.com/webpack-contrib/copy-webpack-plugin/master/LICENSE;md5sum=95a881ed5cb29fc8a0fa0356525f30ac;downloadfilename=LICENSE-copy-webpack-plugin \
	https://raw.githubusercontent.com/webpack-contrib/css-loader/master/LICENSE;md5sum=95a881ed5cb29fc8a0fa0356525f30ac;downloadfilename=LICENSE-css-loader \
	https://raw.githubusercontent.com/cssnano/cssnano/master/LICENSE-MIT;md5sum=5a9c687fbbd43eb51c08313a2cbbf60d;downloadfilename=LICENSE-cssnano \
	https://raw.githubusercontent.com/webpack-contrib/file-loader/master/LICENSE;md5sum=95a881ed5cb29fc8a0fa0356525f30ac;downloadfilename=LICENSE-file-loader \
	https://raw.githubusercontent.com/TypeStrong/fork-ts-checker-webpack-plugin/master/LICENSE;md5sum=e2d85727d6aa24546df3e1975c978407;downloadfilename=LICENSE-fork-ts-checker-webpack-plugin \
	https://raw.githubusercontent.com/jantimon/html-webpack-plugin/master/LICENSE;md5sum=95a881ed5cb29fc8a0fa0356525f30ac;downloadfilename=LICENSE-html-webpack-plugin \
	https://raw.githubusercontent.com/chimurai/http-proxy-middleware/master/LICENSE;md5sum=fa7e7f6d3fb600031b55c641acdac84d;downloadfilename=LICENSE-http-proxy-middleware \
	https://raw.githubusercontent.com/webpack-contrib/mini-css-extract-plugin/master/LICENSE;md5sum=95a881ed5cb29fc8a0fa0356525f30ac;downloadfilename=LICENSE-mini-css-extract-plugin \
	https://raw.githubusercontent.com/webpack-contrib/postcss-loader/master/LICENSE;md5sum=7c6802ed94ac83214d15a26008fa22a5;downloadfilename=LICENSE-postcss-loader \
	https://raw.githubusercontent.com/csstools/postcss-preset-env/master/LICENSE.md;md5sum=f65e988052fdb532de6946a2818ea51e;downloadfilename=LICENSE-postcss-preset-env \
	https://raw.githubusercontent.com/prettier/prettier/master/LICENSE;md5sum=e0b7377e8ff45f6d15cb82b02e64d11d;downloadfilename=LICENSE-prettier \
	https://raw.githubusercontent.com/azz/pretty-quick/master/LICENSE;md5sum=b22fa87f8f83e6a6177c4f943055361c;downloadfilename=LICENSE-pretty-quick \
	https://raw.githubusercontent.com/FullHuman/purgecss/master/LICENSE;md5sum=7c0698ad15fb5a176ada03615061da3a;downloadfilename=LICENSE-purgecss-webpack-plugin \
	https://raw.githubusercontent.com/sass/dart-sass/master/LICENSE;md5sum=7b6bea02f868837cb0fbfefb1b2c4597;downloadfilename=LICENSE-sass \
	https://raw.githubusercontent.com/webpack-contrib/sass-loader/master/LICENSE;md5sum=7c6802ed94ac83214d15a26008fa22a5;downloadfilename=LICENSE-sass-loader \
	https://raw.githubusercontent.com/bhovhannes/svg-url-loader/master/LICENSE;md5sum=4a62da892abc65a1b031cee987ede736;downloadfilename=LICENSE-svg-url-loader \
	https://raw.githubusercontent.com/webpack-contrib/terser-webpack-plugin/master/LICENSE;md5sum=95a881ed5cb29fc8a0fa0356525f30ac;downloadfilename=LICENSE-terser-webpack-plugin \
	https://raw.githubusercontent.com/TypeStrong/ts-loader/master/LICENSE;md5sum=457e68f1b3702e910b854ec3fccf2e0a;downloadfilename=LICENSE-ts-loader \
	https://raw.githubusercontent.com/microsoft/TypeScript/master/LICENSE.txt;md5sum=55a8748c7d5c7253f3e4bb7402ff04db;downloadfilename=LICENSE-TypeScript \
	https://raw.githubusercontent.com/webpack/webpack/master/LICENSE;md5sum=95a881ed5cb29fc8a0fa0356525f30ac;downloadfilename=LICENSE-webpack \
	https://raw.githubusercontent.com/webpack/webpack-cli/master/LICENSE;md5sum=7c6802ed94ac83214d15a26008fa22a5;downloadfilename=LICENSE-webpack-cli \
	https://raw.githubusercontent.com/webpack/webpack-dev-server/master/LICENSE;md5sum=95a881ed5cb29fc8a0fa0356525f30ac;downloadfilename=LICENSE-webpack-dev-server \
	file://avahi/octoprint.service \
	file://nginx/prusa-auth.conf \
	file://nginx/error_401.txt \
	file://nginx/sl1fw \
"

LIC_FILES_CHKSUM = " \
	file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464 \
	file://${WORKDIR}/LICENSE-bulma;md5=814d77c8b54f22875dfdb6f42417ad45 \
	file://${WORKDIR}/LICENSE-i18next;md5=16e7b81632cf4b844087a62a1242bec5 \
	file://${WORKDIR}/LICENSE-i18next-browser-languageDetector;md5=f4112c384a38707998b5a2af4cfc4154 \
	file://${WORKDIR}/LICENSE-i18next-xhr-backend;md5=f4112c384a38707998b5a2af4cfc4154 \
	file://${WORKDIR}/LICENSE-preact;md5=11497b3621048be9724baca2ec0d85ee \
	file://${WORKDIR}/LICENSE-preact-router;md5=8318b79a651efc0165e52d30e14babde \
	file://${WORKDIR}/LICENSE-react-i18next;md5=f4112c384a38707998b5a2af4cfc4154 \
	file://${WORKDIR}/LICENSE-types-react;md5=dbd0ec53724558452cfcfbb506e27104 \
	file://${WORKDIR}/LICENSE-autoprefixer;md5=e0ef868fdaaba6859dcbab082c20439b \
	file://${WORKDIR}/LICENSE-clean-webpack-plugin;md5=0adff6b3db29616057454babc15a4179 \
	file://${WORKDIR}/LICENSE-copy-webpack-plugin;md5=95a881ed5cb29fc8a0fa0356525f30ac \
	file://${WORKDIR}/LICENSE-css-loader;md5=95a881ed5cb29fc8a0fa0356525f30ac \
	file://${WORKDIR}/LICENSE-cssnano;md5=5a9c687fbbd43eb51c08313a2cbbf60d \
	file://${WORKDIR}/LICENSE-file-loader;md5=95a881ed5cb29fc8a0fa0356525f30ac \
	file://${WORKDIR}/LICENSE-fork-ts-checker-webpack-plugin;md5=e2d85727d6aa24546df3e1975c978407 \
	file://${WORKDIR}/LICENSE-html-webpack-plugin;md5=95a881ed5cb29fc8a0fa0356525f30ac \
	file://${WORKDIR}/LICENSE-http-proxy-middleware;md5=fa7e7f6d3fb600031b55c641acdac84d \
	file://${WORKDIR}/LICENSE-mini-css-extract-plugin;md5=95a881ed5cb29fc8a0fa0356525f30ac \
	file://${WORKDIR}/LICENSE-postcss-loader;md5=7c6802ed94ac83214d15a26008fa22a5 \
	file://${WORKDIR}/LICENSE-postcss-preset-env;md5=f65e988052fdb532de6946a2818ea51e \
	file://${WORKDIR}/LICENSE-prettier;md5=e0b7377e8ff45f6d15cb82b02e64d11d \
	file://${WORKDIR}/LICENSE-pretty-quick;md5=b22fa87f8f83e6a6177c4f943055361c \
	file://${WORKDIR}/LICENSE-purgecss-webpack-plugin;md5=7c0698ad15fb5a176ada03615061da3a \
	file://${WORKDIR}/LICENSE-sass;md5=7b6bea02f868837cb0fbfefb1b2c4597 \
	file://${WORKDIR}/LICENSE-sass-loader;md5=7c6802ed94ac83214d15a26008fa22a5 \
	file://${WORKDIR}/LICENSE-svg-url-loader;md5=4a62da892abc65a1b031cee987ede736 \
	file://${WORKDIR}/LICENSE-terser-webpack-plugin;md5=95a881ed5cb29fc8a0fa0356525f30ac \
	file://${WORKDIR}/LICENSE-ts-loader;md5=457e68f1b3702e910b854ec3fccf2e0a \
	file://${WORKDIR}/LICENSE-TypeScript;md5=55a8748c7d5c7253f3e4bb7402ff04db \
	file://${WORKDIR}/LICENSE-webpack;md5=95a881ed5cb29fc8a0fa0356525f30ac \
	file://${WORKDIR}/LICENSE-webpack-cli;md5=7c6802ed94ac83214d15a26008fa22a5 \
	file://${WORKDIR}/LICENSE-webpack-dev-server;md5=95a881ed5cb29fc8a0fa0356525f30ac \
"

LICENSE_${PN} = "GPL-3.0"

# dependencies
LICENSE_${PN}-bulma = "MIT"
LICENSE_${PN}-i18next = "MIT"
LICENSE_${PN}-i18next-browser-languageDetector = "MIT"
LICENSE_${PN}-i18next-xhr-backend = "MIT"
LICENSE_${PN}-preact = "MIT"
LICENSE_${PN}-preact-router = "MIT"
LICENSE_${PN}-react-i18next = "MIT"

# devDependencies
LICENSE_${PN}-autoprefixer = "MIT"
LICENSE_${PN}-clean-webpack-plugin = "MIT"
LICENSE_${PN}-copy-webpack-plugin = "MIT"
LICENSE_${PN}-css-loader = "MIT"
LICENSE_${PN}-cssnano = "MIT"
LICENSE_${PN}-file-loader = "MIT"
LICENSE_${PN}-fork-ts-checker-webpack-plugin = "MIT"
LICENSE_${PN}-html-webpack-plugin = "MIT"
LICENSE_${PN}-http-proxy-middleware = "MIT"
LICENSE_${PN}-i18next-parser = "MIT"
LICENSE_${PN}-mini-css-extract-plugin = "MIT"
LICENSE_${PN}-postcss-loader = "MIT"
LICENSE_${PN}-postcss-preset-env = "CC0-1.0"
LICENSE_${PN}-prettier = "MIT"
LICENSE_${PN}-pretty-quick = "MIT"
LICENSE_${PN}-purgecss-webpack-plugin = "MIT"
LICENSE_${PN}-sass = "MIT"
LICENSE_${PN}-sass-loader = "MIT"
LICENSE_${PN}-svg-url-loader = "MIT"
LICENSE_${PN}-terser-webpack-plugin = "MIT"
LICENSE_${PN}-ts-loader = "MIT"
LICENSE_${PN}-TypeScript = "Apache-2.0"
LICENSE_${PN}-webpack = "MIT"
LICENSE_${PN}-webpack-cli = "MIT"
LICENSE_${PN}-webpack-dev-server = "MIT"

# Doesn't have LICENSE file, just npm description
LICENSE_${PN}-glob-all = "MIT"
LICENSE_${PN}-types-react = "MIT"

inherit useradd

DEPENDS += " nodejs-native python3"
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
