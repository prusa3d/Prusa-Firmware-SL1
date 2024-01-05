SUMMARY = "Prusa Connect Client SLA - python api firmware part running on a64 board"
HOMEPAGE = "https://gitlab.com/prusa3d/sl1/remote-api"
LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "git://gitlab.com/prusa3d/sl1/remote-api.git;protocol=https;branch=master"

SRCREV:pn-${PN} = "2020ade80ae66ade86ca8e326de17ffb52857328"
PACKAGES = "${PN}-dev ${PN}"

DEPENDS += "slafw"
RDEPENDS:${PN} += " \
	python3 \
	python3-pydbus \
	python3-pygobject \
	python3-inotify-simple \
	python3-core \
	slafw \
	prusa-errors \
	prusa-connect-sdk \
	gcode-metadata \
"

FILES:${PN} += "\
	${libdir}/systemd/system/prusa_connect.service\
	${sysconfdir}/systemd/system/multi-user.target.wants/prusa_connect.service\
	${sysconfdir}/sl1fw/remoteConfig.toml\
	/usr/share/dbus-1/system.d\
"

S = "${WORKDIR}/git/prusa_connect"
inherit setuptools3 useradd

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "-f --system projects; -f --system remote_config"
USERADD_PARAM:${PN} = "\
	--system \
	--no-create-home \
	--home-dir /nonexistent \
	--shell /bin/false \
	--groups projects,remote_config \
	--user-group \
	prusa_connect \
"

do_install:append () {
	# Enable prusa_connect
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
	ln -s ${libdir}/systemd/system/prusa_connect.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/prusa_connect.service

	install -d ${D}${sysconfdir}/sl1fw
	touch ${D}${sysconfdir}/sl1fw/remoteConfig.toml
	chmod 664 ${D}${sysconfdir}/sl1fw/remoteConfig.toml
	chgrp remote_config ${D}${sysconfdir}/sl1fw/remoteConfig.toml
}
