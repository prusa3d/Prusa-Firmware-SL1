SUMMARY = "sl1fw - python firmware part running on a64 board" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@github.com/FUTUR3D/DWARF3.git;protocol=ssh \
file://serial.patch \
file://ramdisk.patch \
file://mounts.patch \
file://setup.py.patch \
file://main-setuputils.patch \
file://package.patch \
file://mnt-rootfs.mount \
file://mnt-rootfs.automount \
file://mnt-usb.mount \
file://mnt-usb.automount \
file://50-device-timeout.conf \
file://sl1fw-tmpfiles.conf \
file://sl1fw.service \
file://sl1fw \
"
SRCREV_pn-${PN} = "0e45afbaa73894058d4e274ed199940fab3e34b3"


PACKAGES = "${PN}"

DEPENDS = "nginx"

RDEPENDS_${PN} = "bash"

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
	${libdir}/systemd/system/multi-user.target.wants/sl1fw.service\
	${sysconfdir}/nginx/sites-available/sl1fw\
	${sysconfdir}/nginx/sites-enabled/sl1fw\
	${libdir}/tmpfiles.d/sl1fw.conf\
	${sysconfdir}/sl1fw/hardware.cfg\
	/usr/bin/main.py\
	/mnt/rootfs\
	/mnt/usb\
	/srv/http/intranet\
"

S="${WORKDIR}/git/firmware/home/root"
INTRANET="${WORKDIR}/git/firmware/home/root/sl1fw/intranet"

inherit setuptools

do_install_append () {
	# Systemd units
	install -d ${D}${libdir}/systemd/system
	install ${WORKDIR}/sl1fw.service ${D}${libdir}/systemd/system/sl1fw.service
	install ${WORKDIR}/mnt-rootfs.mount ${D}${libdir}/systemd/system/mnt-rootfs.mount
	install ${WORKDIR}/mnt-rootfs.automount ${D}${libdir}/systemd/system/mnt-rootfs.automount
	install ${WORKDIR}/mnt-usb.mount ${D}${libdir}/systemd/system/mnt-usb.mount
	install ${WORKDIR}/mnt-usb.automount ${D}${libdir}/systemd/system/mnt-usb.automount
	install -d ${D}${libdir}/systemd/system/dev-sda1.device.d
	install ${WORKDIR}/50-device-timeout.conf ${D}${libdir}/systemd/system/dev-sda1.device.d/50-device-timeout.conf
	install -d ${D}${libdir}/systemd/system/dev-sda2.device.d
	install ${WORKDIR}/50-device-timeout.conf ${D}${libdir}/systemd/system/dev-sda2.device.d/50-device-timeout.conf
	
	# Enable sl1fw service and mounts
	install -d ${D}${libdir}/systemd/system/multi-user.target.wants	
	ln -s ${libdir}/systemd/system/sl1fw.service ${D}${libdir}/systemd/system/multi-user.target.wants/sl1fw.service
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
	install -d ${D}${sysconfdir}/sl1fw
	install ${S}/sl1fw/hardware.cfg ${D}${sysconfdir}/sl1fw/hardware.cfg
	
	# Firmware intranet
	install --owner www-data --group www-data -d ${D}/srv/http/intranet
	install --owner www-data --group www-data -d ${D}/srv/http/intranet/static
	install --owner www-data --group www-data  ${INTRANET}/static/SB_dwarf3_network.svg ${D}/srv/http/intranet/static/SB_dwarf3_network.svg
	install --owner www-data --group www-data  ${INTRANET}/static/SB_dwarf3_back.svg ${D}/srv/http/intranet/static/SB_dwarf3_back.svg
	install --owner www-data --group www-data  ${INTRANET}/static/favicon_dwarf3.ico ${D}/srv/http/intranet/static/favicon_dwarf3.ico
	install --owner www-data --group www-data  ${INTRANET}/static/B_settings.svg ${D}/srv/http/intranet/static/B_settings.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_projectsettings.svg ${D}/srv/http/intranet/static/B_projectsettings.svg
	install --owner www-data --group www-data  ${INTRANET}/static/SB_empty.svg ${D}/srv/http/intranet/static/SB_empty.svg
	install --owner www-data --group www-data  ${INTRANET}/static/SB_cworks_setup.svg ${D}/srv/http/intranet/static/SB_cworks_setup.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_print.svg ${D}/srv/http/intranet/static/B_print.svg
	install --owner www-data --group www-data  ${INTRANET}/static/deadman.svg ${D}/srv/http/intranet/static/deadman.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_turnoff.svg ${D}/srv/http/intranet/static/B_turnoff.svg
	install --owner www-data --group www-data  ${INTRANET}/static/jquery-3.3.1.min.js ${D}/srv/http/intranet/static/jquery-3.3.1.min.js
	install --owner www-data --group www-data  ${INTRANET}/static/B_control.svg ${D}/srv/http/intranet/static/B_control.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_updown.svg ${D}/srv/http/intranet/static/B_updown.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_upturnoff.svg ${D}/srv/http/intranet/static/B_upturnoff.svg
	install --owner www-data --group www-data  ${INTRANET}/static/exception.css ${D}/srv/http/intranet/static/exception.css
	install --owner www-data --group www-data  ${INTRANET}/static/B_refresh.svg ${D}/srv/http/intranet/static/B_refresh.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_back.svg ${D}/srv/http/intranet/static/B_back.svg
	install --owner www-data --group www-data  ${INTRANET}/static/logo_cworks.svg ${D}/srv/http/intranet/static/logo_cworks.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_secondadd.svg ${D}/srv/http/intranet/static/B_secondadd.svg
	install --owner www-data --group www-data  ${INTRANET}/static/style.css ${D}/srv/http/intranet/static/style.css
	install --owner www-data --group www-data  ${INTRANET}/static/SB_cworks_network.svg ${D}/srv/http/intranet/static/SB_cworks_network.svg
	install --owner www-data --group www-data  ${INTRANET}/static/SB_cworks_back.svg ${D}/srv/http/intranet/static/SB_cworks_back.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_systeminfo.svg ${D}/srv/http/intranet/static/B_systeminfo.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_changesettings.svg ${D}/srv/http/intranet/static/B_changesettings.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_exposuretime.svg ${D}/srv/http/intranet/static/B_exposuretime.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_usb.svg ${D}/srv/http/intranet/static/B_usb.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_secondsub.svg ${D}/srv/http/intranet/static/B_secondsub.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_tankreset.svg ${D}/srv/http/intranet/static/B_tankreset.svg
	install --owner www-data --group www-data  ${INTRANET}/static/SB_cworks_info.svg ${D}/srv/http/intranet/static/SB_cworks_info.svg
	install --owner www-data --group www-data  ${INTRANET}/static/SB_dwarf3_setup.svg ${D}/srv/http/intranet/static/SB_dwarf3_setup.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_up50mm.svg ${D}/srv/http/intranet/static/B_up50mm.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_up5mm.svg ${D}/srv/http/intranet/static/B_up5mm.svg
	install --owner www-data --group www-data  ${INTRANET}/static/logo_dwarf3.svg ${D}/srv/http/intranet/static/logo_dwarf3.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_continue.svg ${D}/srv/http/intranet/static/B_continue.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_lan.svg ${D}/srv/http/intranet/static/B_lan.svg
	install --owner www-data --group www-data  ${INTRANET}/static/SB_dwarf3_info.svg ${D}/srv/http/intranet/static/SB_dwarf3_info.svg
	install --owner www-data --group www-data  ${INTRANET}/static/B_wait.svg ${D}/srv/http/intranet/static/B_wait.svg
	install --owner www-data --group www-data  ${INTRANET}/static/favicon_cworks.ico ${D}/srv/http/intranet/static/favicon_cworks.ico
	install --owner www-data --group www-data  ${INTRANET}/gunicorn_test.sh ${D}/srv/http/intranet/gunicorn_test.sh
	install --owner www-data --group www-data  ${INTRANET}/sl1.py ${D}/srv/http/intranet/sl1.py
	install --owner www-data --group www-data  -d ${D}/srv/http/intranet/templates
	install --owner www-data --group www-data  ${INTRANET}/templates/_adminC.html ${D}/srv/http/intranet/templates/_adminC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_patternsH.html ${D}/srv/http/intranet/templates/_patternsH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_waitH.html ${D}/srv/http/intranet/templates/_waitH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_sysinfoC.html ${D}/srv/http/intranet/templates/_sysinfoC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_homeH.html ${D}/srv/http/intranet/templates/_homeH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_controlC.html ${D}/srv/http/intranet/templates/_controlC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_homeprintC.html ${D}/srv/http/intranet/templates/_homeprintC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_head_BW.html ${D}/srv/http/intranet/templates/_head_BW.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_head_SWI.html ${D}/srv/http/intranet/templates/_head_SWI.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_homeprintH.html ${D}/srv/http/intranet/templates/_homeprintH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_sourceselectH.html ${D}/srv/http/intranet/templates/_sourceselectH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_testhwH.html ${D}/srv/http/intranet/templates/_testhwH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_confirmH.html ${D}/srv/http/intranet/templates/_confirmH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_settingsC.html ${D}/srv/http/intranet/templates/_settingsC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_errorH.html ${D}/srv/http/intranet/templates/_errorH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_head_WI.html ${D}/srv/http/intranet/templates/_head_WI.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_head.html ${D}/srv/http/intranet/templates/_head.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_aboutC.html ${D}/srv/http/intranet/templates/_aboutC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_waitC.html ${D}/srv/http/intranet/templates/_waitC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_printC.html ${D}/srv/http/intranet/templates/_printC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_projsettingsC.html ${D}/srv/http/intranet/templates/_projsettingsC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_netinfoC.html ${D}/srv/http/intranet/templates/_netinfoC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_testhwC.html ${D}/srv/http/intranet/templates/_testhwC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_errorC.html ${D}/srv/http/intranet/templates/_errorC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/layout.html ${D}/srv/http/intranet/templates/layout.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_head_BWI.html ${D}/srv/http/intranet/templates/_head_BWI.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_head_B.html ${D}/srv/http/intranet/templates/_head_B.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_projsettingsH.html ${D}/srv/http/intranet/templates/_projsettingsH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_confirmC.html ${D}/srv/http/intranet/templates/_confirmC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_sysinfoH.html ${D}/srv/http/intranet/templates/_sysinfoH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_patternsC.html ${D}/srv/http/intranet/templates/_patternsC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_homeC.html ${D}/srv/http/intranet/templates/_homeC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_controlH.html ${D}/srv/http/intranet/templates/_controlH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_settingsH.html ${D}/srv/http/intranet/templates/_settingsH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_aboutH.html ${D}/srv/http/intranet/templates/_aboutH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_changeH.html ${D}/srv/http/intranet/templates/_changeH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_sourceselectC.html ${D}/srv/http/intranet/templates/_sourceselectC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_changeC.html ${D}/srv/http/intranet/templates/_changeC.html
	install --owner www-data --group www-data  ${INTRANET}/templates/exception.html ${D}/srv/http/intranet/templates/exception.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_adminH.html ${D}/srv/http/intranet/templates/_adminH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_printH.html ${D}/srv/http/intranet/templates/_printH.html
	install --owner www-data --group www-data  ${INTRANET}/templates/_netinfoH.html ${D}/srv/http/intranet/templates/_netinfoH.html
}
