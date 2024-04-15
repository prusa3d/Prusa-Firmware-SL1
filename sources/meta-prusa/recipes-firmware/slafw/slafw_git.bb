SUMMARY = "slafw - python firmware part running on a64 board"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
	file://projects-tmpfiles.conf \
	file://slafw.conf \
"

SRC_URI:prepend:prusa64-sl1 = " \
	git://gitlab.com/prusa3d/sl1/sla-fw.git;protocol=https;branch=master \
"

SRCREV:pn-${PN}:prusa64-sl1 = "8c0c3572438f7aa0970427aa10ef9894da29422a"

PACKAGES = "${PN}-dev ${PN}"

DEPENDS += "python3 gettext-native"

RDEPENDS:${PN} += " \
	python3 \
	bash \
	avrdude \
	mc-fw \
	udev-usbmount \
	python3-pyserial \
	python3-numpy \
	python3-gpiod \
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
	python3-pywayland \
	python3-spidev \
	python3-smbus2 \
	python3-pycairo \
	librsvg (>= 2.46) \
	ssh-updater \
	python-uinput \
"

FILES:${PN} += "\
	${libdir}/systemd/system/slafw.service\
	${sysconfdir}/systemd/system/multi-user.target.wants/slafw.service\
	${libdir}/tmpfiles.d/slafw-tmpfiles.conf\
	${libdir}/tmpfiles.d/projects-tmpfiles.conf\
	${sysconfdir}/sl1fw/hardware.cfg\
	/usr/bin/main.py\
	/usr/share/scripts\
	/usr/share/factory/defaults\
	/usr/share/dbus-1/system.d\
	/usr/lib/sysusers.d/slafw.conf\
"
FILES:${PN}:remove = "${sysconfdir}/sl1fw/slafw-logger.json"
FILES:${PN}-dev = "${sysconfdir}/sl1fw/slafw-logger.json"

S="${WORKDIR}/git"

inherit setuptools3 systemd

do_install:append () {
	# Install projects tmpfiles
	install -d ${D}${libdir}/tmpfiles.d
	install --mode 644 ${WORKDIR}/projects-tmpfiles.conf ${D}${libdir}/tmpfiles.d/projects.conf

	# Install projects group
	install -d ${D}${libdir}/sysusers.d/
	install --mode 644 ${WORKDIR}/slafw.conf ${D}${libdir}/sysusers.d/slafw.conf
}

SYSTEMD_SERVICE:${PN} = "${BPN}.service"
