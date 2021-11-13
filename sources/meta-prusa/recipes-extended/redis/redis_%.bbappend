FILESEXTRAPATHS:prepend := "${THISDIR}/redis:"

SRC_URI += "\
file://redis.service \
file://redis.conf \
file://redis-tmpfiles.conf \
"

FILES:${PN} += "\
${libdir}/tmpfiles.d/redis.conf\
"

do_install:append () {
	# tmpfiles.d
	install -d ${D}${libdir}/tmpfiles.d
	install ${WORKDIR}/redis-tmpfiles.conf ${D}${libdir}/tmpfiles.d/redis.conf
}
