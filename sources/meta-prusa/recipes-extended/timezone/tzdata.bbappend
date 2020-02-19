RDEPENDS_${PN}_remove = "tzdata-core tzdata-misc tzdata-posix tzdata-right"
DEFAULT_TIMEZONE = "Europe/Prague"

FILES_tzdata-core_remove = "\
	${sysconfdir}/localtime \
	${sysconfdir}/timezone \
	${datadir}/zoneinfo/Universal \
"

FILES_${PN}_append = "\
	${sysconfdir}/localtime \
	${sysconfdir}/timezone \
	${datadir}/zoneinfo/Universal \
	/usr/share/factory/etc/localtime \
	/usr/share/factory/etc/timezone \
"

do_install_append() {
	install --directory ${D}/usr/share/factory/etc
	cp -av ${D}/${sysconfdir}/localtime ${D}/usr/share/factory/etc/localtime
	cp -av ${D}/${sysconfdir}/timezone ${D}/usr/share/factory/etc/timezone
}
