SUMMARY = "sl1fw - python firmware part running on a64 board" 

LICENSE = "CLOSED"

SRC_URI = "\
git://git@github.com/FUTUR3D/DWARF3.git;protocol=ssh \
file://serial.patch \
file://ramdisk.patch \
file://mounts.patch \
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
	${sysconfdir}/systemd/system/sl1fw.service\
	${sysconfdir}/systemd/system/mnt-rootfs.mount\
	${sysconfdir}/systemd/system/mnt-rootfs.automount\
	${sysconfdir}/systemd/system/local-fs.target.wants/mnt-rootfs.automount\
	${sysconfdir}/systemd/system/mnt-usb.mount\
	${sysconfdir}/systemd/system/mnt-usb.automount\
	${sysconfdir}/systemd/system/local-fs.target.wants/mnt-usb.automount\
	${sysconfdir}/systemd/system/dev-sda1.device.d/50-device-timeout.conf\
	${sysconfdir}/systemd/system/dev-sda2.device.d/50-device-timeout.conf\
	${sysconfdir}/systemd/system/multi-user.target.wants/sl1fw.service\
	${sysconfdir}/nginx/sites-available/sl1fw\
	${sysconfdir}/nginx/sites-enabled/sl1fw\
	${sysconfdir}/tmpfiles.d/sl1fw.conf\
	/home/root/sl1fw\
	/mnt/rootfs\
	/mnt/usb\
	/srv/http/intranet\
"

S="${WORKDIR}/git"

do_install () {
	# Systemd units
	install -d ${D}${sysconfdir}/systemd/system
	install ${S}/firmware/etc/systemd/system/sl1fw.service ${D}${sysconfdir}/systemd/system/sl1fw.service
	install ${WORKDIR}/mnt-rootfs.mount ${D}${sysconfdir}/systemd/system/mnt-rootfs.mount
	install ${WORKDIR}/mnt-rootfs.automount ${D}${sysconfdir}/systemd/system/mnt-rootfs.automount
	install ${WORKDIR}/mnt-usb.mount ${D}${sysconfdir}/systemd/system/mnt-usb.mount
	install ${WORKDIR}/mnt-usb.automount ${D}${sysconfdir}/systemd/system/mnt-usb.automount
	install -d ${D}${sysconfdir}/systemd/system/dev-sda1.device.d
	install ${WORKDIR}/50-device-timeout.conf ${D}${sysconfdir}/systemd/system/dev-sda1.device.d/50-device-timeout.conf
	install -d ${D}${sysconfdir}/systemd/system/dev-sda2.device.d
	install ${WORKDIR}/50-device-timeout.conf ${D}${sysconfdir}/systemd/system/dev-sda2.device.d/50-device-timeout.conf
	
	# Enable sl1fw service and mounts
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants	
	ln -s ${sysconfdir}/systemd/system/sl1fw.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/sl1fw.service
	install -d ${D}${sysconfdir}/systemd/system/local-fs.target.wants
	ln -s ${sysconfdir}/systemd/system/mnt-rootfs.automount ${D}${sysconfdir}/systemd/system/local-fs.target.wants/mnt-rootfs.automount
	ln -s ${sysconfdir}/systemd/system/mnt-usb.automount ${D}${sysconfdir}/systemd/system/local-fs.target.wants/mnt-usb.automount
	
	# Nginx site
	install -d ${D}${sysconfdir}/nginx/sites-available
	install -d ${D}${sysconfdir}/nginx/sites-enabled
	install ${WORKDIR}/sl1fw ${D}${sysconfdir}/nginx/sites-available/sl1fw
	ln -s ${sysconfdir}/nginx/sites-available/sl1fw ${D}${sysconfdir}/nginx/sites-enabled/sl1fw
	
	# Firmware mount points
	install -d ${D}/mnt/rootfs
	install -d ${D}/mnt/usb
	
	# Firmware tmpfiles
	install -d ${D}${sysconfdir}/tmpfiles.d
	install ${WORKDIR}/sl1fw-tmpfiles.conf ${D}${sysconfdir}/tmpfiles.d/sl1fw.conf
	
	# Firmware application
	install -d ${D}/home/root/sl1fw
	install ${S}/firmware/home/root/sl1fw/defines.py ${D}/home/root/sl1fw/defines.py
	install ${S}/firmware/home/root/sl1fw/hardware.cfg ${D}/home/root/sl1fw/hardware.cfg
	install ${S}/firmware/home/root/sl1fw/libConfig.py ${D}/home/root/sl1fw/libConfig.py
	install ${S}/firmware/home/root/sl1fw/libDisplay.py ${D}/home/root/sl1fw/libDisplay.py
	install ${S}/firmware/home/root/sl1fw/libExposure.py ${D}/home/root/sl1fw/libExposure.py
	install ${S}/firmware/home/root/sl1fw/libHardware.py ${D}/home/root/sl1fw/libHardware.py
	install ${S}/firmware/home/root/sl1fw/libInternet.py ${D}/home/root/sl1fw/libInternet.py
	install ${S}/firmware/home/root/sl1fw/libNextion.py ${D}/home/root/sl1fw/libNextion.py
	install ${S}/firmware/home/root/sl1fw/libPages.py ${D}/home/root/sl1fw/libPages.py
	install ${S}/firmware/home/root/sl1fw/libPrinter.py ${D}/home/root/sl1fw/libPrinter.py
	install ${S}/firmware/home/root/sl1fw/libScreen.py ${D}/home/root/sl1fw/libScreen.py
	install ${S}/firmware/home/root/sl1fw/libWeb.py ${D}/home/root/sl1fw/libWeb.py
	install ${S}/firmware/home/root/sl1fw/main.py ${D}/home/root/sl1fw/main.py
	install ${S}/firmware/home/root/sl1fw/test_configs.py ${D}/home/root/sl1fw/test_configs.py
	install ${S}/firmware/home/root/sl1fw/test_numpy.py ${D}/home/root/sl1fw/test_numpy.py
	install ${S}/firmware/home/root/sl1fw/test_pygame.py ${D}/home/root/sl1fw/test_pygame.py
	install ${S}/firmware/home/root/sl1fw/test_tinyled.py ${D}/home/root/sl1fw/test_tinyled.py
	install ${S}/firmware/home/root/sl1fw/test_web.py ${D}/home/root/sl1fw/test_web.py
	install -d ${D}/home/root/sl1fw/data
	install ${S}/firmware/home/root/sl1fw/data/mrizka8_1440x2560.png ${D}/home/root/sl1fw/data/mrizka8_1440x2560.png
	install ${S}/firmware/home/root/sl1fw/data/NX4827T043_011R-prusa-rot.tft ${D}/home/root/sl1fw/data/NX4827T043_011R-prusa-rot.tft
	install ${S}/firmware/home/root/sl1fw/data/sachovnice8_1440x2560.png ${D}/home/root/sl1fw/data/sachovnice8_1440x2560.png
	install ${S}/firmware/home/root/sl1fw/data/bludiste_1440x2560.png ${D}/home/root/sl1fw/data/bludiste_1440x2560.png
	install ${S}/firmware/home/root/sl1fw/data/mrizka16_1440x2560.png ${D}/home/root/sl1fw/data/mrizka16_1440x2560.png
	install ${S}/firmware/home/root/sl1fw/data/NX4827T043_011R-prusa-nor.tft ${D}/home/root/sl1fw/data/NX4827T043_011R-prusa-nor.tft
	install ${S}/firmware/home/root/sl1fw/data/sachovnice16_1440x2560.png ${D}/home/root/sl1fw/data/sachovnice16_1440x2560.png
	install -d ${D}/home/root/sl1fw/scripts
	install ${S}/firmware/home/root/sl1fw/scripts/set_hostname.sh ${D}/home/root/sl1fw/scripts/set_hostname.sh
	install ${S}/firmware/home/root/sl1fw/scripts/rsync_net.sh ${D}/home/root/sl1fw/scripts/rsync_net.sh
	install ${S}/firmware/home/root/sl1fw/scripts/rsync-key ${D}/home/root/sl1fw/scripts/rsync-key
	install ${S}/firmware/home/root/sl1fw/scripts/exclude.txt ${D}/home/root/sl1fw/scripts/exclude.txt
	install ${S}/firmware/home/root/sl1fw/scripts/rsync_usb.sh ${D}/home/root/sl1fw/scripts/rsync_usb.sh
	
	# Firmware intranet
	install --owner www-data --group www-data -d ${D}/srv/http/intranet
	install --owner www-data --group www-data -d ${D}/srv/http/intranet/static
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/SB_dwarf3_network.svg ${D}/srv/http/intranet/static/SB_dwarf3_network.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/SB_dwarf3_back.svg ${D}/srv/http/intranet/static/SB_dwarf3_back.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/favicon_dwarf3.ico ${D}/srv/http/intranet/static/favicon_dwarf3.ico
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_settings.svg ${D}/srv/http/intranet/static/B_settings.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_projectsettings.svg ${D}/srv/http/intranet/static/B_projectsettings.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/SB_empty.svg ${D}/srv/http/intranet/static/SB_empty.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/SB_cworks_setup.svg ${D}/srv/http/intranet/static/SB_cworks_setup.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_print.svg ${D}/srv/http/intranet/static/B_print.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/deadman.svg ${D}/srv/http/intranet/static/deadman.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_turnoff.svg ${D}/srv/http/intranet/static/B_turnoff.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/jquery-3.3.1.min.js ${D}/srv/http/intranet/static/jquery-3.3.1.min.js
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_control.svg ${D}/srv/http/intranet/static/B_control.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_updown.svg ${D}/srv/http/intranet/static/B_updown.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_upturnoff.svg ${D}/srv/http/intranet/static/B_upturnoff.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/exception.css ${D}/srv/http/intranet/static/exception.css
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_refresh.svg ${D}/srv/http/intranet/static/B_refresh.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_back.svg ${D}/srv/http/intranet/static/B_back.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/logo_cworks.svg ${D}/srv/http/intranet/static/logo_cworks.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_secondadd.svg ${D}/srv/http/intranet/static/B_secondadd.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/style.css ${D}/srv/http/intranet/static/style.css
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/SB_cworks_network.svg ${D}/srv/http/intranet/static/SB_cworks_network.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/SB_cworks_back.svg ${D}/srv/http/intranet/static/SB_cworks_back.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_systeminfo.svg ${D}/srv/http/intranet/static/B_systeminfo.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_changesettings.svg ${D}/srv/http/intranet/static/B_changesettings.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_exposuretime.svg ${D}/srv/http/intranet/static/B_exposuretime.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_usb.svg ${D}/srv/http/intranet/static/B_usb.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_secondsub.svg ${D}/srv/http/intranet/static/B_secondsub.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_tankreset.svg ${D}/srv/http/intranet/static/B_tankreset.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/SB_cworks_info.svg ${D}/srv/http/intranet/static/SB_cworks_info.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/SB_dwarf3_setup.svg ${D}/srv/http/intranet/static/SB_dwarf3_setup.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_up50mm.svg ${D}/srv/http/intranet/static/B_up50mm.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_up5mm.svg ${D}/srv/http/intranet/static/B_up5mm.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/logo_dwarf3.svg ${D}/srv/http/intranet/static/logo_dwarf3.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_continue.svg ${D}/srv/http/intranet/static/B_continue.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_lan.svg ${D}/srv/http/intranet/static/B_lan.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/SB_dwarf3_info.svg ${D}/srv/http/intranet/static/SB_dwarf3_info.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/B_wait.svg ${D}/srv/http/intranet/static/B_wait.svg
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/static/favicon_cworks.ico ${D}/srv/http/intranet/static/favicon_cworks.ico
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/gunicorn_test.sh ${D}/srv/http/intranet/gunicorn_test.sh
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/sl1.py ${D}/srv/http/intranet/sl1.py
	install --owner www-data --group www-data  -d ${D}/srv/http/intranet/templates
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_adminC.html ${D}/srv/http/intranet/templates/_adminC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_patternsH.html ${D}/srv/http/intranet/templates/_patternsH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_waitH.html ${D}/srv/http/intranet/templates/_waitH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_sysinfoC.html ${D}/srv/http/intranet/templates/_sysinfoC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_homeH.html ${D}/srv/http/intranet/templates/_homeH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_controlC.html ${D}/srv/http/intranet/templates/_controlC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_homeprintC.html ${D}/srv/http/intranet/templates/_homeprintC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_head_BW.html ${D}/srv/http/intranet/templates/_head_BW.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_head_SWI.html ${D}/srv/http/intranet/templates/_head_SWI.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_homeprintH.html ${D}/srv/http/intranet/templates/_homeprintH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_sourceselectH.html ${D}/srv/http/intranet/templates/_sourceselectH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_testhwH.html ${D}/srv/http/intranet/templates/_testhwH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_confirmH.html ${D}/srv/http/intranet/templates/_confirmH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_settingsC.html ${D}/srv/http/intranet/templates/_settingsC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_errorH.html ${D}/srv/http/intranet/templates/_errorH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_head_WI.html ${D}/srv/http/intranet/templates/_head_WI.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_head.html ${D}/srv/http/intranet/templates/_head.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_aboutC.html ${D}/srv/http/intranet/templates/_aboutC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_waitC.html ${D}/srv/http/intranet/templates/_waitC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_printC.html ${D}/srv/http/intranet/templates/_printC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_projsettingsC.html ${D}/srv/http/intranet/templates/_projsettingsC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_netinfoC.html ${D}/srv/http/intranet/templates/_netinfoC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_testhwC.html ${D}/srv/http/intranet/templates/_testhwC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_errorC.html ${D}/srv/http/intranet/templates/_errorC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/layout.html ${D}/srv/http/intranet/templates/layout.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_head_BWI.html ${D}/srv/http/intranet/templates/_head_BWI.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_head_B.html ${D}/srv/http/intranet/templates/_head_B.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_projsettingsH.html ${D}/srv/http/intranet/templates/_projsettingsH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_confirmC.html ${D}/srv/http/intranet/templates/_confirmC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_sysinfoH.html ${D}/srv/http/intranet/templates/_sysinfoH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_patternsC.html ${D}/srv/http/intranet/templates/_patternsC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_homeC.html ${D}/srv/http/intranet/templates/_homeC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_controlH.html ${D}/srv/http/intranet/templates/_controlH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_settingsH.html ${D}/srv/http/intranet/templates/_settingsH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_aboutH.html ${D}/srv/http/intranet/templates/_aboutH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_changeH.html ${D}/srv/http/intranet/templates/_changeH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_sourceselectC.html ${D}/srv/http/intranet/templates/_sourceselectC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_changeC.html ${D}/srv/http/intranet/templates/_changeC.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/exception.html ${D}/srv/http/intranet/templates/exception.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_adminH.html ${D}/srv/http/intranet/templates/_adminH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_printH.html ${D}/srv/http/intranet/templates/_printH.html
	install --owner www-data --group www-data  ${S}/firmware/home/root/sl1fw/intranet/templates/_netinfoH.html ${D}/srv/http/intranet/templates/_netinfoH.html
}
