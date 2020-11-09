FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
	file://0001-enable-all-sysrq-commands.patch \
	file://0002-timesyncd-google.patch \
	file://0004-send-shdn-cmd-to-mc-during-poweroff.patch \
	file://journal.conf \
	file://max_use.conf \
	file://logind-no-auto-vt.conf \
	file://logind-no-auto-off.conf \
"

RDEPENDS_${PN}_remove = "volatile-binds"

do_install_append() {
	if ! ${@bb.utils.contains('PACKAGECONFIG', 'resolved', 'true', 'false', d)}; then
		sed -i -e "s%^L! /etc/resolv.conf.*$%L! /etc/resolv.conf - - - - ../run/systemd/resolve/stub-resolv.conf%g" \
			${D}${exec_prefix}/lib/tmpfiles.d/etc.conf
	fi

	# Persistent journal directory tmpfiles configuration
	install -d ${D}${libdir}/tmpfiles.d
	install ${WORKDIR}/journal.conf ${D}${libdir}/tmpfiles.d/journal.conf

	# Size configuration
	install -d ${D}${libdir}/systemd/journald.conf.d
	install ${WORKDIR}/max_use.conf ${D}${libdir}/systemd/journald.conf.d/max_use.conf

	install -d ${D}${libdir}/systemd/logind.conf.d

	# No automatic gettty spawn
	install --mode 644 ${WORKDIR}/logind-no-auto-vt.conf ${D}${libdir}/systemd/logind.conf.d/no-auto-vt.conf

	# No poweroff on power button
	install --mode 644 ${WORKDIR}/logind-no-auto-off.conf ${D}${libdir}/systemd/logind.conf.d/no-auto-off.conf
}
