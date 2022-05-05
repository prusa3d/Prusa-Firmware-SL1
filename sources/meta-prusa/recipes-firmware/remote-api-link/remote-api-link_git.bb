SUMMARY = "remote_api_link - python api firmware part running on a64 board"
HOMEPAGE = "https://gitlab.com/prusa3d/sl1/remote-api"
LICENSE = "GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "git://gitlab.com/prusa3d/sl1/remote-api.git;protocol=https;nobranch=1"

SRCREV:pn-${PN} = "ef68888d14749f5bd330bfbd215a5bca8f1beb2d"
PACKAGES = "${PN}-dev ${PN}"

DEPENDS += "slafw gettext-native"
RDEPENDS:${PN} += " \
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
	python3-xml \
	python3-streaming-form-data \
	slafw \
	prusa-errors \
"

FILES:${PN} += " \
	${libdir}/systemd/system/remote_api_link.service \
	${libdir}/tmpfiles.d/remote_api_link-tmpfiles.conf \
	${libdir}/sysusers.d/remote_api_link-user.conf \
	${sysconfdir}/systemd/system/multi-user.target.wants/remote_api_link.service \
	/usr/bin/main_api.py \"

FILES:${PN}:remove = "${localstatedir}/remote_api_link/loggerConfig.json"
FILES:${PN}-dev = "${localstatedir}/remote_api_link/loggerConfig.json"

S = "${WORKDIR}/git/remote_api_link"
inherit setuptools3 systemd useradd

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "-f --system projects"
USERADD_PARAM:${PN} = "\
	--system \
	--no-create-home \
	--home-dir /nonexistent \
	--shell /bin/false \
	--groups projects \
	--user-group \
	remote_api_link \
"

do_install:append () {
	rmdir ${D}/usr/share
}

SYSTEMD_SERVICE:${PN} = "remote_api_link.service"
