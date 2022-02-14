require klipper.inc

S = "${WORKDIR}/git"

RDEPENDS:${PN} = "\
	python3 \
	python3-pyserial \
	python3-cffi \
	python3-greenlet \
	python3-jinja2 \
"

inherit setuptools3

do_install:append () {
	install -d ${D}${datadir}/klipper

	lib=$(find ${D} -type f -name 'c_helper*.so')
	mv $lib $(dirname $lib)/c_helper.so
}
