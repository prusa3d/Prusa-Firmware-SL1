RDEPENDS:${PN}:remove = "tzdata-arctic tzdata-misc tzdata-posix tzdata-right"
DEFAULT_TIMEZONE = "Europe/Prague"

FILES:tzdata-core:remove = "\
	${sysconfdir}/localtime \
	${sysconfdir}/timezone \
	${datadir}/zoneinfo/Universal \
"

FILES:${PN}:append = "\
	${sysconfdir}/localtime \
	${sysconfdir}/timezone \
	${datadir}/zoneinfo/Universal \
	/usr/share/factory/etc/localtime \
	/usr/share/factory/etc/timezone \
"

do_install:append() {
	install --directory ${D}/usr/share/factory/etc
	cp -av ${D}/${sysconfdir}/localtime ${D}/usr/share/factory/etc/localtime
	cp -av ${D}/${sysconfdir}/timezone ${D}/usr/share/factory/etc/timezone

	find ${D}/${datadir}/zoneinfo -maxdepth 1 -type f -exec rm "{}" \; # remove all files in base folder
	cp -v "${S}${datadir}/zoneinfo/Universal" ${D}${datadir}/zoneinfo # keep Universal timezone installed
	rm -rf ${D}/${datadir}/zoneinfo/Arctic
	rm -rf ${D}/${datadir}/zoneinfo/Chile
	rm -rf ${D}/${datadir}/zoneinfo/Etc
	rm -rf ${D}/${datadir}/zoneinfo/posix
	rm -rf ${D}/${datadir}/zoneinfo/right
}
