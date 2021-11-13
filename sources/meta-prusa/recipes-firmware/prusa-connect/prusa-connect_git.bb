SUMMARY = "Prusa Connect Client SL1 - python api firmware part running on a64 board"
HOMEPAGE = "https://gitlab.com/prusa3d/sl1/remote-api"
LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "git://git@gitlab.com/prusa3d/sl1/remote-api.git;protocol=ssh;branch=master"

SRCREV:pn-${PN} = "41c9e898c41f44ac338214ec71343a23c65e3f71"
PACKAGES = "${PN}-dev ${PN}"

DEPENDS += "sl1fw"
RDEPENDS:${PN} += " \
	python3 \
	python3-pydbus \
	python3-pygobject \
	python3-core \
	sl1fw \
	prusa-errors \
	prusa-connect-sdk \
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
