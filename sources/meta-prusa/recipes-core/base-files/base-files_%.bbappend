hostname="prusa-sl1"

do_install_append() {
	rm ${D}${sysconfdir}/fstab
}
