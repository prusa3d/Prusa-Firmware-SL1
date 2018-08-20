DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_packagegroup-prusa-sla = "nginx redis gunicorn python rsync \
	sla-edid etc zsh samba \
	python-flask python-gevent python-pyroute2 python-redis \
	python-pygame python-jrpc python-flask-sse python-pyserial \
	python-pip \
	"
