FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
	file://0001-enable-all-sysrq-commands.patch \
	file://0002-timesyncd-google.patch \
	file://0003-journal-gatewayd-localhost.patch \
	file://journal.site \
	file://journal.conf \
	file://max_use.conf \
"

PACKAGECONFIG_append = " microhttpd"
PACKAGECONFIG_remove = "networkd"

RDEPENDS_${PN}_remove = "volatile-binds"

FILES_${PN}-journal-gatewayd += " \
	${sysconfdir}/nginx/sites-enabled/journal \
	${sysconfdir}/nginx/sites-available/journal \
"

do_install_append() {
	if ! ${@bb.utils.contains('PACKAGECONFIG', 'resolved', 'true', 'false', d)}; then
		sed -i -e "s%^L! /etc/resolv.conf.*$%L! /etc/resolv.conf - - - - ../run/systemd/resolve/stub-resolv.conf%g" \
			${D}${exec_prefix}/lib/tmpfiles.d/etc.conf
	fi

	# nginx journal-gatewayd reverse proxy site
	install -d ${D}${sysconfdir}/nginx/sites-available
	install ${WORKDIR}/journal.site ${D}${sysconfdir}/nginx/sites-available/journal
	
	# enable journal-gatewayd reverse proxy site
	install -d ${D}${sysconfdir}/nginx/sites-enabled
	ln -s ${sysconfdir}/nginx/sites-available/journal ${D}${sysconfdir}/nginx/sites-enabled/journal

	# Persistent journal directory tmpfiles configuration
	install -d ${D}${libdir}/tmpfiles.d
	install ${WORKDIR}/journal.conf ${D}${libdir}/tmpfiles.d/journal.conf

	# Size configuration
	install -d ${D}${libdir}/systemd/journald.conf.d
	install ${WORKDIR}/max_use.conf ${D}${libdir}/systemd/journald.conf.d/max_use.conf
}
