hostname="prusa-sl1"

do_install:append() {
	rm ${D}${sysconfdir}/fstab
}
