FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "\
	file://0001-enable-all-sysrq-commands.patch \
	file://0002-timesyncd-google.patch \
	file://0003-localed-accept-locale-even-if-not-installed.patch \
	file://0004-send-shdn-cmd-to-mc-during-poweroff.patch \
	file://0006-resolved-implement-full-Legacy-Unicast-Response.patch \
	file://journal.conf \
	file://max_use.conf \
	file://logind-no-auto-vt.conf \
	file://logind-no-auto-off.conf \
"

RDEPENDS:${PN}:remove = "volatile-binds"

do_install:append() {
	if ! ${@bb.utils.contains('PACKAGECONFIG', 'resolved', 'true', 'false', d)}; then
		sed -i -e "s%^L! /etc/resolv.conf.*$%L! /etc/resolv.conf - - - - ../run/systemd/resolve/stub-resolv.conf%g" \
			${D}${exec_prefix}/lib/tmpfiles.d/etc.conf
		install -d ${D}${systemd_system_unitdir}
		ln -s ../systemd-resolved.service ${D}${systemd_system_unitdir}/multi-user.target.wants/systemd-resolved.service
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

	rm -f ${D}${sysconfdir}/tmpfiles.d/00-create-volatile.conf

	# remove 'init' symlinks to systemd binary
	rm -f ${D}/init ${D}${sbindir}/init
}
