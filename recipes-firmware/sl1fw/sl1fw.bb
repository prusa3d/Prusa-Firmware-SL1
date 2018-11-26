SUMMARY = "sl1fw - python firmware part running on a64 board" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/SLA_fw.git;protocol=ssh;branch=deploy \
file://mnt-rootfs.mount \
file://mnt-rootfs.automount \
file://mnt-usb.mount \
file://mnt-usb.automount \
file://50-device-timeout.conf \
file://sl1fw-tmpfiles.conf \
file://sl1fw.service \
file://sl1fw \
file://fb.modes \
"
SRCREV_pn-${PN} = "689071d3d29e85faefc3a1154398b622c5477588"


PACKAGES = "${PN}"

RDEPENDS_${PN} = "bash nginx python-websocket-server python-pygame python-pyserial python-pyroute2 python-numpy python-six"

FILES_${PN} = "\
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
	${libdir}/tmpfiles.d/sl1fw.conf\
	${sysconfdir}/sl1fw/hardware.cfg\
	/usr/bin/main.py\
	/mnt/rootfs\
	/mnt/usb\
	/srv/http/intranet\
	/usr/share/scripts\
	${sysconfdir}/sl1fw/hardware.cfg\
	${sysconfdir}/fb.modes\
"


S="${WORKDIR}/git/firmware/home/root"
INTRANET=""

inherit setuptools

do_install_append () {
	# Systemd units
	install -d ${D}${libdir}/systemd/system
	install --mode 644 ${WORKDIR}/sl1fw.service ${D}${libdir}/systemd/system/sl1fw.service
	install --mode 644 ${WORKDIR}/mnt-rootfs.mount ${D}${libdir}/systemd/system/mnt-rootfs.mount
	install --mode 644 ${WORKDIR}/mnt-rootfs.automount ${D}${libdir}/systemd/system/mnt-rootfs.automount
	install --mode 644 ${WORKDIR}/mnt-usb.mount ${D}${libdir}/systemd/system/mnt-usb.mount
	install --mode 644 ${WORKDIR}/mnt-usb.automount ${D}${libdir}/systemd/system/mnt-usb.automount
	install -d ${D}${libdir}/systemd/system/dev-sda1.device.d
	install --mode 644 ${WORKDIR}/50-device-timeout.conf ${D}${libdir}/systemd/system/dev-sda1.device.d/50-device-timeout.conf
	install -d ${D}${libdir}/systemd/system/dev-sda2.device.d
	install --mode 644 ${WORKDIR}/50-device-timeout.conf ${D}${libdir}/systemd/system/dev-sda2.device.d/50-device-timeout.conf
	
	# Enable sl1fw service and mounts
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants	
	ln -s ${libdir}/systemd/system/sl1fw.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/sl1fw.service
	install -d ${D}${libdir}/systemd/system/local-fs.target.wants
	ln -s ${libdir}/systemd/system/mnt-rootfs.automount ${D}${libdir}/systemd/system/local-fs.target.wants/mnt-rootfs.automount
	ln -s ${libdir}/systemd/system/mnt-usb.automount ${D}${libdir}/systemd/system/local-fs.target.wants/mnt-usb.automount
	
	# Nginx site
	install -d ${D}${sysconfdir}/nginx/sites-available
	install -d ${D}${sysconfdir}/nginx/sites-enabled
	install ${WORKDIR}/sl1fw ${D}${sysconfdir}/nginx/sites-available/sl1fw
	ln -s ${sysconfdir}/nginx/sites-available/sl1fw ${D}${sysconfdir}/nginx/sites-enabled/sl1fw
	
	# Firmware mount points
	install -d ${D}/mnt/rootfs
	install -d ${D}/mnt/usb
	
	# Firmware tmpfiles
	install -d ${D}${libdir}/tmpfiles.d
	install ${WORKDIR}/sl1fw-tmpfiles.conf ${D}${libdir}/tmpfiles.d/sl1fw.conf
	
	# Firmware application configuration
	#install -d ${D}${sysconfdir}/sl1fw
	#install ${S}/sl1fw/hardware.cfg ${D}${sysconfdir}/sl1fw/hardware.cfg
	
	# Firmware data
	#install -d ${D}/usr/share/sl1fw
	#cp -r ${S}/sl1fw/data ${D}/usr/share/sl1fw/data
	
	# Firmware intranet
	#install -d ${D}/srv/http
	#cp -r ${S}/sl1fw/intranet ${D}/srv/http/intranet
	#chown www-data:www-data ${D}/srv/http/intranet
	
	# Framebuffer configuration
	install -d ${D}${sysconfdir}
	install ${WORKDIR}/fb.modes ${D}${sysconfdir}/fb.modes
}
