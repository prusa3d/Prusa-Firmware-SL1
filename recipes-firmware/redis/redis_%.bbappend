FILESEXTRAPATHS_prepend := "${THISDIR}/redis:"

SRC_URI += "\
file://redis.service \
file://redis.conf \
file://redis-tmpfiles.conf \
"

FILES_${PN} += "\
/etc/tmpfiles.d/redis.conf\
"

do_install_append () {
	# tmpfiles.d
	install -d ${D}/etc/tmpfiles.d
	install ${WORKDIR}/redis-tmpfiles.conf ${D}/etc/tmpfiles.d/redis.conf
}
