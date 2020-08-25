SUMMARY = "sl1fw - python firmware part running on a64 board"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
	git://git@gitlab.com/prusa3d/sl1/a64-fw.git;protocol=ssh;branch=master \
	file://0001-sl1fw-set-path-to-weston-framebuffer-s-fb.patch \
	file://projects-tmpfiles.conf \
	file://sl1fw.conf \
"
SRCREV_pn-${PN} = "e711fbd7a7bb9007d31879ff958294195b2fe19f"

PACKAGES = "${PN}-dev ${PN}"

DEPENDS += "python3 gettext-native"

RDEPENDS_${PN} += " \
	python3 \
	bash \
	avrdude \
	api-keygen \
	udev-usbmount \
	wifi-config \
	fbset \
	python3-websocket-server \
	python3-pyserial \
	python3-numpy \
	python3-jinja2 \
	python3-gpio \
	python3-pydbus \
	python3-misc \
	python3-pygobject \
	python3-systemd \
	python3-bitstring \
	python3-logging \
	python3-future \
	python3-paho-mqtt \
	python3-toml \
	python3-pillow \
	python3-distro \
	python3-readerwriterlock \
	python3-deprecation \
	python3-psutil \
	python3-evdev \
	python3-pysignal \
	python3-requests \
	python3-aiohttp \
	prusa-errors \
"

FILES_${PN} += "\
	${libdir}/systemd/system/sl1fw.service\
	${sysconfdir}/systemd/system/multi-user.target.wants/sl1fw.service\
	${libdir}/tmpfiles.d/sl1fw-tmpfiles.conf\
	${libdir}/tmpfiles.d/projects-tmpfiles.conf\
	${sysconfdir}/sl1fw/hardware.cfg\
	/usr/bin/main.py\
	/usr/share/scripts\
	/usr/share/factory/defaults\
	/usr/share/dbus-1/system.d\
	/usr/lib/sysusers.d/sl1fw.conf\
"
FILES_${PN}_remove = "${sysconfdir}/sl1fw/loggerConfig.json"
FILES_${PN}-dev = "${sysconfdir}/sl1fw/loggerConfig.json"

S="${WORKDIR}/git/firmware"
INTRANET=""

inherit setuptools3 systemd

do_install_append () {
	# Install projects tmpfiles
	install -d ${D}${libdir}/tmpfiles.d
	install --mode 644 ${WORKDIR}/projects-tmpfiles.conf ${D}${libdir}/tmpfiles.d/projects.conf

	# Install projects group
	install -d ${D}${libdir}/sysusers.d/
	install --mode 644 ${WORKDIR}/sl1fw.conf ${D}${libdir}/sysusers.d/sl1fw.conf
}

SYSTEMD_SERVICE_${PN} = "${BPN}.service"
