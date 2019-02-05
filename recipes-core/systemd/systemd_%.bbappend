FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
	file://0001-enable-all-sysrq-commands.patch \
	file://0002-timesyncd-google.patch \
	file://0003-journal-gatewayd-localhost.patch \
	file://journal.site \
"

PACKAGECONFIG_append = " microhttpd"

FILES_${PN}-journal-gatewayd += " \
	${sysconfdir}/nginx/sites-enabled/journal \
	${sysconfdir}/nginx/sites-available/journal \
"

do_install_append() {
	# nginx journal-gatewayd reverse proxy site
	install -d ${D}${sysconfdir}/nginx/sites-available
	install ${WORKDIR}/journal.site ${D}${sysconfdir}/nginx/sites-available/journal
	
	# enable journal-gatewayd reverse proxy site
	install -d ${D}${sysconfdir}/nginx/sites-enabled
	ln -s ${sysconfdir}/nginx/sites-available/journal ${D}${sysconfdir}/nginx/sites-enabled/journal
}
