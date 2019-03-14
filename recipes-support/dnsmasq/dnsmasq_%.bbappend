FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "\
	file://dnsmasq.conf \
	file://dnsmasq.service \
	file://resolved-stub-disable.conf \
"

SYSTEMD_AUTO_ENABLE_${PN} = "disable"

PACKAGECONFIG = ""

CONFFILES_${PN} = ""
FILES_${PN} += " \
	${datadir}/dnsmasq.conf \
	/usr/lib/systemd/resolved.conf.d/99-disablestub.conf \
"

do_install () {
    oe_runmake "PREFIX=${D}${prefix}" \
               "BINDIR=${D}${bindir}" \
               "MANDIR=${D}${mandir}" \
               install

	install -d ${D}${datadir}/
	install -m 644 ${WORKDIR}/dnsmasq.conf ${D}${datadir}/

	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/dnsmasq.service ${D}${systemd_system_unitdir}/dnsmasq.service

	install -m 0755 ${S}/contrib/lease-tools/dhcp_release ${D}${bindir}
	
	install -d ${D}/usr/lib/systemd/resolved.conf.d
	install -m 0644 ${WORKDIR}/resolved-stub-disable.conf ${D}/usr/lib/systemd/resolved.conf.d/99-disablestub.conf
}
