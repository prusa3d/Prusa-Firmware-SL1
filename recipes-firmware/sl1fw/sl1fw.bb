SUMMARY = "sl1fw - python firmware part running on a64 board" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/SLA_fw.git;protocol=ssh;branch=master \
file://mnt-rootfs.mount \
file://mnt-rootfs.automount \
file://mnt-usb.mount \
file://mnt-usb.automount \
file://50-device-timeout.conf \
file://fb.modes \
"
SRCREV_pn-${PN} = "bc43bd06e873fabbcdb0be531efb89acbef090f2"


PACKAGES = "${PN}"

RDEPENDS_${PN} = "bash nginx python-websocket-server python-pygame python-pyserial python-pyroute2 python-numpy python-six python-numpy python-jinja2 python-gpio python-pydrm"

FILES_${PN} += "\
	${libdir}/systemd/system/sl1fw.service\
	${libdir}/systemd/system/mnt-rootfs.mount\
	${libdir}/systemd/system/mnt-rootfs.automount\
	${libdir}/systemd/system/local-fs.target.wants/mnt-rootfs.automount\
	${libdir}/systemd/system/mnt-usb.mount\
	${libdir}/systemd/system/mnt-usb.automount\
	${libdir}/systemd/system/local-fs.target.wants/mnt-usb.automount\
	${libdir}/systemd/system/dev-sda1.device.d/50-device-timeout.conf\
	${libdir}/systemd/system/dev-sda2.device.d/50-device-timeout.conf\
	${sysconfdir}/systemd/system/multi-user.target.wants/sl1fw.service\
	${sysconfdir}/nginx/sites-available/sl1fw\
	${sysconfdir}/nginx/sites-enabled/sl1fw\
	${libdir}/tmpfiles.d/sl1fw-tmpfiles.conf\
	${sysconfdir}/sl1fw/hardware.cfg\
	/usr/bin/main.py\
	/mnt/rootfs\
	/mnt/usb\
	/srv/http/intranet\
	/usr/share/scripts\
	${sysconfdir}/fb.modes\
"


S="${WORKDIR}/git/firmware"
INTRANET=""

inherit setuptools

do_install_append () {
	# Enable sl1fw
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants	
	ln -s ${libdir}/systemd/system/sl1fw.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/sl1fw.service
	
	# USB, rootfs mount points
	install -d ${D}/mnt/rootfs
	install -d ${D}/mnt/usb
	
	# Systemd mounts: usb, rootfs
	install -d ${D}${libdir}/systemd/system
	install --mode 644 ${WORKDIR}/mnt-rootfs.mount ${D}${libdir}/systemd/system/mnt-rootfs.mount
	install --mode 644 ${WORKDIR}/mnt-rootfs.automount ${D}${libdir}/systemd/system/mnt-rootfs.automount
	install --mode 644 ${WORKDIR}/mnt-usb.mount ${D}${libdir}/systemd/system/mnt-usb.mount
	install --mode 644 ${WORKDIR}/mnt-usb.automount ${D}${libdir}/systemd/system/mnt-usb.automount
	install -d ${D}${libdir}/systemd/system/dev-sda1.device.d
	install --mode 644 ${WORKDIR}/50-device-timeout.conf ${D}${libdir}/systemd/system/dev-sda1.device.d/50-device-timeout.conf
	install -d ${D}${libdir}/systemd/system/dev-sda2.device.d
	install --mode 644 ${WORKDIR}/50-device-timeout.conf ${D}${libdir}/systemd/system/dev-sda2.device.d/50-device-timeout.conf
		
	# Enable usb, rootfs mounts
	install -d ${D}${libdir}/systemd/system/local-fs.target.wants
	ln -s ${libdir}/systemd/system/mnt-rootfs.automount ${D}${libdir}/systemd/system/local-fs.target.wants/mnt-rootfs.automount
	ln -s ${libdir}/systemd/system/mnt-usb.automount ${D}${libdir}/systemd/system/local-fs.target.wants/mnt-usb.automount
	
	# Enable nginx site
	install -d ${D}${sysconfdir}/nginx/sites-enabled
	ln -s ${sysconfdir}/nginx/sites-available/sl1fw ${D}${sysconfdir}/nginx/sites-enabled/sl1fw
	
	# Framebuffer configuration
	install -d ${D}${sysconfdir}
	install ${WORKDIR}/fb.modes ${D}${sysconfdir}/fb.modes
}
