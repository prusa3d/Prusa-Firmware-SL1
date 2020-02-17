SUMMARY = "web ui - static web files firmware part running on a64 board" 
HOMEPAGE = "https://github.com/prusa3d/Prusa-Connect-Local"
LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRCREV_pn-${PN} = "40e1c13737ca4912e27ed87efc4cd1c096bfce65"
SRC_URI = " \
    git://git@github.com/prusa3d/Prusa-Connect-Local.git;protocol=ssh;branch=sl1-1.3 \
"

inherit useradd

DEPENDS += " nodejs nodejs-native"
FILES_${PN} += " \
   /srv/http/intranet \
"
S = "${WORKDIR}/git"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = " \
    --system --no-create-home \
    --groups www-data \
    --user-group www"

do_compile() {
    npm install 
    node node_modules/webpack/bin/webpack.js --config ./webpack.config.js --mode production --env.apiKey=developer --env.printer='Original Prusa SL1'
}

do_install_append () {

    # static web files
    install -m 755 -d ${D}/srv/http/intranet/
    install --mode 755 ${S}/dist/* ${D}/srv/http/intranet/
    chown www:www-data -R ${D}/srv/http/intranet/
}

