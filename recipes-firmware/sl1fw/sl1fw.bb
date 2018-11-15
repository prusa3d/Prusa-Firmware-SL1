SUMMARY = "sl1fw - python firmware part running on a64 board" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@github.com/FUTUR3D/DWARF3.git;protocol=ssh \
file://serial.patch \
file://home-root-ramdisk.mount \
file://home-root-rootfs.mount \
file://home-root-rootfs.automount \
file://home-root-usb.mount \
file://home-root-usb.automount \
file://50-device-timeout.conf \
file://sl1fw.service \
file://redis.service \
"
SRCREV_pn-${PN} = "e3d6dd2941f4abba3facbf5e8f0fb048d1971aa5"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "bash"

FILES_${PN} = "\
	/etc/systemd/system/sl1fw.service\
	/etc/systemd/system/gunicorn.service\
	/etc/systemd/system/redis.service\
	/etc/systemd/system/gunicorn.socket\
	/etc/systemd/system/home-root-ramdisk.mount\
	/etc/systemd/system/home-root-rootfs.mount\
	/etc/systemd/system/home-root-rootfs.automount\
	/etc/systemd/system/home-root-usb.mount\
	/etc/systemd/system/home-root-usb.automount\
	/etc/systemd/system/dev-sda1.device.d/50-device-timeout.conf \
	/etc/systemd/system/dev-sda2.device.d/50-device-timeout.conf \
	/etc/systemd/system/multi-user.target.wants/sl1fw.service\
	/etc/tmpfiles.d/gunicorn.conf\
	/etc/tmpfiles.d/redis.conf\
	/etc/nginx/nginx.conf\
	/etc/nginx/sites-available/default\
	/etc/nginx/sites-enabled/default\
	/home/root/sl1fw\
	/home/root/rootfs\
	/home/root/ramdisk\
	/home/root/usb\
"

S = "${WORKDIR}"

do_install () {
	# Systemd files
	install -d ${D}/etc/systemd/system
	install ${S}/git/firmware/etc/systemd/system/sl1fw.service ${D}/etc/systemd/system/sl1fw.service
	install ${S}/git/firmware/etc/systemd/system/gunicorn.service ${D}/etc/systemd/system/gunicorn.service
	install ${S}/git/firmware/etc/systemd/system/gunicorn.socket ${D}/etc/systemd/system/gunicorn.socket
	install ${S}/redis.service ${D}/etc/systemd/system/redis.service
	install ${S}/home-root-ramdisk.mount ${D}/etc/systemd/system/home-root-ramdisk.mount
	install ${S}/home-root-rootfs.mount ${D}/etc/systemd/system/home-root-rootfs.mount
	install ${S}/home-root-rootfs.automount ${D}/etc/systemd/system/home-root-rootfs.automount
	install ${S}/home-root-usb.mount ${D}/etc/systemd/system/home-root-usb.mount
	install ${S}/home-root-usb.automount ${D}/etc/systemd/system/home-root-usb.automount
	install -d ${D}/etc/systemd/system/dev-sda1.device.d
	install ${S}/50-device-timeout.conf ${D}/etc/systemd/system/dev-sda1.device.d/50-device-timeout.conf
	install -d ${D}/etc/systemd/system/dev-sda2.device.d
	install ${S}/50-device-timeout.conf ${D}/etc/systemd/system/dev-sda2.device.d/50-device-timeout.conf
	
	# Enable sl1fw service
	install -d ${D}/etc/systemd/system/multi-user.target.wants	
	ln -s /etc/systemd/system/sl1fw.service ${D}/etc/systemd/system/multi-user.target.wants/sl1fw.service
	
	# Tmpfiles
	install -d ${D}/etc/tmpfiles.d
	install ${S}/git/firmware/etc/tmpfiles.d/redis-server.conf ${D}/etc/tmpfiles.d/redis.conf
	install ${S}/git/firmware/etc/tmpfiles.d/gunicorn.conf ${D}/etc/tmpfiles.d/gunicorn.conf
	
	# Nginx configuration
	install -d ${D}/etc/nginx
	install -d ${D}/etc/nginx/sites-available
	install -d ${D}/etc/nginx/sites-enabled
	install ${S}/git/firmware/etc/nginx/nginx.conf ${D}/etc/nginx/nginx.conf
	install ${S}/git/firmware/etc/nginx/sites-available/default ${D}/etc/nginx/sites-available/default
	ln -s /etc/nginx/sites-available/default ${D}/etc/nginx/sites-enabled/default
	
	# Firmware application
	install -d ${D}/home/root/sl1fw
	install -d ${D}/home/root/ramdisk
	install -d ${D}/home/root/rootfs
	install -d ${D}/home/root/usb
	cp -r ${S}/git/firmware/home/root/sl1fw ${D}/home/root
}
