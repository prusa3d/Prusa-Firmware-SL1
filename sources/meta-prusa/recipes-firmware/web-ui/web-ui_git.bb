SUMMARY = "web ui - static web files firmware part running on a64 board" 
HOMEPAGE = "https://github.com/prusa3d/Prusa-Connect-Local"
LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRCREV_pn-${PN} = "46e4ee29bc40b1f043297f88b856ba56102d9de2"
SRC_URI = " \
    git://git@github.com/prusa3d/Prusa-Connect-Local.git;protocol=ssh;branch=master \
"

inherit useradd

DEPENDS += " nodejs nodejs-native python3"
FILES_${PN} += " \
   /srv/http/intranet \
   ${sysconfdir}/sl1fw/static_api.key \
"
S = "${WORKDIR}/git"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = " \
    --system --no-create-home \
    --groups www-data \
    --user-group www"

do_compile() {
    KEY=$(python3 -c "import secrets; print(secrets.token_hex(16))")
    echo -n $KEY > api.key
    npm install 
    node node_modules/webpack/bin/webpack.js --config ./webpack.config.js --mode production --env.apiKey=$KEY --env.printer=sl1
}

do_install_append () {

    # static web files
    install -m 755 -d ${D}/srv/http/intranet/
    cp -R --no-preserve=ownership ${S}/dist/* ${D}/srv/http/intranet/
    chmod -R 755 ${D}/srv/http/intranet/
    chown www:www-data -R ${D}/srv/http/intranet/
    install -m 755 -d ${D}${sysconfdir}/sl1fw
    install --mode 755 ${S}/api.key ${D}${sysconfdir}/sl1fw/static_api.key
}
