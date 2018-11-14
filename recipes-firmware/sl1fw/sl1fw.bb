SUMMARY = "sl1fw - python firmware part running on a64 board" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@github.com/FUTUR3D/DWARF3.git;protocol=ssh;branch=work \
file://serial.patch \
file://home-root-ramdisk.mount \
file://home-root-rootfs.mount \
file://home-root-rootfs.automount \
file://home-root-usb.mount \
file://home-root-usb.automount \
file://sl1fw.service \
"
SRCREV_pn-${PN} = "5fc55dcdbb70f0fdd91d8c72ef301d82869d4aad"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "bash"

FILES_${PN} = "\
	/etc/systemd/system/sl1fw.service\
	/etc/systemd/system/gunicorn.service\
	/etc/systemd/system/redis-server.service\
	/etc/systemd/system/gunicorn.socket\
	/etc/systemd/system/home-root-ramdisk.mount\
	/etc/systemd/system/home-root-rootfs.mount\
	/etc/systemd/system/home-root-rootfs.automount\
	/etc/systemd/system/home-root-usb.mount\
	/etc/systemd/system/home-root-usb.automount\
	/etc/systemd/system/multi-user.target.wants/sl1fw.service\
	/etc/systemd/system/multi-user.target.wants/home-root-ramdisk.mount\
	/etc/systemd/system/multi-user.target.wants/home-root-rootfs.automount\
	/etc/systemd/system/multi-user.target.wants/home-root-usb.automount\
	/etc/tmpfiles.d/gunicorn.conf\
	/etc/tmpfiles.d/redis-server.conf\
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
	install ${S}/git/firmware/etc/systemd/system/redis-server.service ${D}/etc/systemd/system/redis-server.service
	install ${S}/home-root-ramdisk.mount ${D}/etc/systemd/system/home-root-ramdisk.mount
	install ${S}/home-root-rootfs.mount ${D}/etc/systemd/system/home-root-rootfs.mount
	install ${S}/home-root-rootfs.automount ${D}/etc/systemd/system/home-root-rootfs.automount
	install ${S}/home-root-usb.mount ${D}/etc/systemd/system/home-root-usb.mount
	install ${S}/home-root-usb.automount ${D}/etc/systemd/system/home-root-usb.automount
	
	# Enable sl1fw service and mounts
	install -d ${D}/etc/systemd/system/multi-user.target.wants	
	ln -s /etc/systemd/system/sl1fw.service ${D}/etc/systemd/system/multi-user.target.wants/sl1fw.service
	ln -s /etc/systemd/system/home-root-ramdisk.mount ${D}/etc/systemd/system/multi-user.target.wants/home-root-ramdisk.mount
	ln -s /etc/systemd/system/home-root-usb.automount ${D}/etc/systemd/system/multi-user.target.wants/home-root-usb.automount
	ln -s /etc/systemd/system/home-root-rootfs.automount ${D}/etc/systemd/system/multi-user.target.wants/home-root-rootfs.automount
	
	# Tmpfiles
	install -d ${D}/etc/tmpfiles.d
	install ${S}/git/firmware/etc/tmpfiles.d/redis-server.conf ${D}/etc/tmpfiles.d/redis-server.conf
	install ${S}/git/firmware/etc/tmpfiles.d/gunicorn.conf ${D}/etc/tmpfiles.d/gunicorn.conf
	
	# Firmware application
	install -d ${D}/home/root/sl1fw
	install -d ${D}/home/root/ramdisk
	install -d ${D}/home/root/rootfs
	install -d ${D}/home/root/usb
	cp -r ${S}/git/firmware/home/root/sl1fw ${D}/home/root
}
