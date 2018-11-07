SUMMARY = "sl1fw - python firmware part running on a64 board" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@github.com/FUTUR3D/DWARF3.git;protocol=ssh;branch=work \
file://serial.patch \
"
SRCREV_pn-${PN} = "707bc4a0455d48c311ff1606fe3834b0ed0ecd0e"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "bash"

FILES_${PN} = "\
	/etc/systemd/system/sl1fw.service\
	/etc/systemd/system/gunicorn.service\
	/etc/systemd/system/redis-server.service\
	/etc/systemd/system/gunicorn.socket\
	/etc/systemd/system/multi-user.target.wants/sl1fw.service\
	/etc/tmpfiles.d/gunicorn.conf\
	/etc/tmpfiles.d/redis-server.conf\
	/home/root/sl1fw\
	/home/root/rootfs\
	/home/root/ramdisk\
"

S = "${WORKDIR}/git"

do_install () {
	install -d ${D}/etc/systemd/system
	install ${S}/firmware/etc/systemd/system/sl1fw.service ${D}/etc/systemd/system/sl1fw.service
	install ${S}/firmware/etc/systemd/system/gunicorn.service ${D}/etc/systemd/system/gunicorn.service
	install ${S}/firmware/etc/systemd/system/gunicorn.socket ${D}/etc/systemd/system/gunicorn.socket
	install ${S}/firmware/etc/systemd/system/redis-server.service ${D}/etc/systemd/system/redis-server.service
	
	install -d ${D}/etc/tmpfiles.d
	install ${S}/firmware/etc/tmpfiles.d/redis-server.conf ${D}/etc/tmpfiles.d/redis-server.conf
	install ${S}/firmware/etc/tmpfiles.d/gunicorn.conf ${D}/etc/tmpfiles.d/gunicorn.conf
	
	install -d ${D}/etc/systemd/system/multi-user.target.wants	
	ln -s /etc/systemd/system/sl1fw.service ${D}/etc/systemd/system/multi-user.target.wants/sl1fw.service
	
	install -d ${D}/home/root/sl1fw
	install -d ${D}/home/root/ramdisk
	install -d ${D}/home/root/rootfs
	cp -r ${S}/firmware/home/root/sl1fw ${D}/home/root
}
