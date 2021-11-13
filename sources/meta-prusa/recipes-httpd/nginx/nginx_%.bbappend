FILESEXTRAPATHS:prepend := "${THISDIR}/nginx:"

SRC_URI += "\
    git://github.com/atomx/nginx-http-auth-digest.git;destsuffix=git/digest \
    file://nginx.conf \
"
SRCREV = "cd8641886c873cf543255aeda20d23e4cd603d05"

EXTRA_OECONF += " --add-module=${WORKDIR}/git/digest"

do_install:append() {
    rm -f ${D}${sysconfdir}/nginx/sites-enabled/default_server
}
