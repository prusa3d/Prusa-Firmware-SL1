SUMMARY = "sl1fw api - python api firmware part running on a64 board"
HOMEPAGE = "https://gitlab.com/prusa3d/sl1/remote-api"
LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "git://git@gitlab.com/prusa3d/sl1/remote-api.git;protocol=ssh;branch=master"

SRCREV_pn-${PN} = "21e2a9ae41cd1a968ab382f510463a1c787d5e62"
PACKAGES = "${PN}-dev ${PN}"

DEPENDS += "sl1fw"
RDEPENDS_${PN} += " \
	python3 \
	python3-flask \
	python3-gevent \
	python3-pydbus \
	python3-pygobject \
	python3-compression \
	python3-core \
	python3-crypt \
	python3-datetime \
	python3-json \
	python3-logging \
	python3-misc \
	python3-netclient \
	python3-stringold \
	python3-typing \
	python3-xml \
	sl1fw \
"

FILES_${PN} += " \
	${libdir}/systemd/system/sl1fw_api.service \
	${libdir}/tmpfiles.d/sl1fw_api-tmpfiles.conf \
	${libdir}/sysusers.d/sl1fw_api-user.conf \
	${sysconfdir}/systemd/system/multi-user.target.wants/sl1fw_api.service \
	/usr/bin/main_api.py \"

FILES_${PN}_remove = "${localstatedir}/sl1fw_api/loggerConfig.json"
FILES_${PN}-dev = "${localstatedir}/sl1fw_api/loggerConfig.json"

S = "${WORKDIR}/git"
inherit setuptools3 useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "\
	--system \
	--no-create-home \
	--home-dir /nonexistent \
	--shell /bin/false \
	--groups projects \
	--user-group \
	sl1fw_api \
"
GROUPADD_PARAM_${PN} = "--system projects"

do_install_append () {

	# Enable sl1fw_api
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
	ln -s ${libdir}/systemd/system/sl1fw_api.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/sl1fw_api.service

	rmdir ${D}/usr/share
}
