require klipper.inc

S = "${WORKDIR}/git"
SRC_URI += "file://klippy.service"

RDEPENDS:${PN} = "\
	g0-mcu \
	python3 \
	python3-pyserial \
	python3-cffi \
	python3-greenlet \
	python3-jinja2 \
"

inherit setuptools3 systemd

do_install:append () {
	install -d ${D}${systemd_system_unitdir}
	install -d ${D}${datadir}/klipper
	install -m 644 ${WORKDIR}/klippy.service ${D}${systemd_system_unitdir}/

	lib=$(find ${D} -type f -name 'c_helper*.so')
	mv $lib $(dirname $lib)/c_helper.so
}

SYSTEMD_SERVICE:${PN} = "klippy.service"
