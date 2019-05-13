SUMMARY = "WSGI HTTP Server for UNIX"
DESCRIPTION = "\
  Gunicorn ‘Green Unicorn’ is a Python WSGI HTTP Server for UNIX. It’s \
  a pre-fork worker model ported from Ruby’s Unicorn project. The \
  Gunicorn server is broadly compatible with various web frameworks, \
  simply implemented, light on server resource usage, and fairly speedy. \
  " 
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=19a2e253a273e390cd1b91d19b6ee236"

SRC_URI = "\
https://pypi.python.org/packages/source/g/gunicorn/${BPN}-${PV}.tar.gz \
file://gunicorn.service \
file://gunicorn.socket \
file://gunicorn.conf \
"
SRC_URI[md5sum] = "eaa72bff5341c05169b76ce3dcbb8140"
SRC_URI[sha256sum] = "82715511fb6246fad4ba66d812eb93416ae8371b464fa88bf3867c9c177daa14"

FILES_${PN} += "\
/etc/systemd/system/gunicorn.service\
/etc/systemd/system/gunicorn.socket\
/etc/tmpfiles.d/gunicorn.conf\
"

inherit setuptools

do_install_append () {
	# Systemd units
	install -d ${D}/etc/systemd/system
	install ${WORKDIR}/gunicorn.service ${D}/etc/systemd/system/gunicorn.service
	install ${WORKDIR}/gunicorn.socket ${D}/etc/systemd/system/gunicorn.socket
	
	# tmpfiles.d
	install -d ${D}/etc/tmpfiles.d
	install ${WORKDIR}/gunicorn.conf ${D}/etc/tmpfiles.d/gunicorn.conf
}
