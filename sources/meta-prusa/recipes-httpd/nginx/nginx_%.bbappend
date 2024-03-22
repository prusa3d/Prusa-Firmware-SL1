FILESEXTRAPATHS:prepend := "${THISDIR}/nginx:"

SRC_URI += "\
    git://github.com/atomx/nginx-http-auth-digest.git;destsuffix=git/digest \
    file://nginx.conf \
    file://htdigest-keygen.sh \
    file://htdigest-keygen.service \
"
SRCREV = "cd8641886c873cf543255aeda20d23e4cd603d05"

EXTRA_OECONF += " --add-module=${WORKDIR}/git/digest"

FILES:${PN}:append = " \
	${libdir}/systemd/system/htdigest-keygen.service \
    ${bindir}/htdigest-keygen.sh \
"

do_install:append() {
    rm -f ${D}${sysconfdir}/nginx/sites-enabled/default_server

	# htdigest-keygen service
	install -d ${D}${systemd_system_unitdir}
	install --mode 644 ${WORKDIR}/htdigest-keygen.service ${D}${systemd_system_unitdir}
	
	# htdigest-keygen script
	install -d ${D}${bindir}
	install --mode 755 ${WORKDIR}/htdigest-keygen.sh ${D}${bindir}
	
	# Ensure folder for storing credentials exists
	install -d ${D}${sysconfdir}/sl1fw
}

SYSTEMD_SERVICE:${PN}:append = " htdigest-keygen.service"
