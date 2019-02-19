LICENSE = "CLOSED"

SRC_URI = " \
	file://20-etc.preset			\
	file://10-wlan.network			\
	file://20-eth.network			\
	file://bashrc				\
	file://profile				\
	file://sshd_config	\
	file://base-feeds.conf \
"

CONFFILES = "							\
	${sysconfdir}/profile					\
	${sysconfdir}/bash/bashrc				\
	${sysconfdir}/systemd/network/10-wlan.network		\
	${sysconfdir}/systemd/network/20-eth.network		\
	${sysconfdir}/ssh/sshd_config	\
	${sysconfdir}/opkg/base-feeds.conf \
"

FILES_${PN} = "							\
	${sysconfdir}/profile					\
	${sysconfdir}/bash/bashrc				\
	${sysconfdir}/systemd/network/10-wlan.network		\
	${sysconfdir}/systemd/network/20-eth.network		\
	${systemd_unitdir}/system-preset/20-etc.preset		\
	${sysconfdir}/ssh/sshd_config	\
	${sysconfdir}/opkg/base-feeds.conf \
"

do_install() {
	install -d ${D}${sysconfdir}
	install -m 644 ${WORKDIR}/profile			${D}${sysconfdir}/
	install -d ${D}${sysconfdir}/bash
	install -m 644 ${WORKDIR}/bashrc			${D}${sysconfdir}/bash/
	install -d ${D}${sysconfdir}/systemd/network
	install -m 644 ${WORKDIR}/10-wlan.network		${D}${sysconfdir}/systemd/network/
	install -m 644 ${WORKDIR}/20-eth.network		${D}${sysconfdir}/systemd/network/
	install -d ${D}${systemd_unitdir}/system-preset
	install -m 644 ${WORKDIR}/20-etc.preset			${D}${systemd_unitdir}/system-preset/
	install -d ${D}${sysconfdir}/ssh
	install -m 644 ${WORKDIR}/sshd_config			${D}${sysconfdir}/ssh/
	install -d ${D}${sysconfdir}/opkg
	install -m 644 ${WORKDIR}/base-feeds.conf 		${D}${sysconfdir}/opkg/
}

PACKAGE_WRITE_DEPS_append = " systemd-systemctl-native"

pkg_postinst_${PN} () {
	if [ -n "$D" ]; then
		OPTS="--root=$D"
	fi
	systemctl $OPTS enable sshd.socket
	install -d $D${sysconfdir}/systemd/system/multi-user.target.wants
	ln -sf ${systemd_system_unitdir}/wpa_supplicant@.service  \
		$D${sysconfdir}/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service

	sed 's,/root:/bin/sh,/root:/bin/bash,' -i $D${sysconfdir}/passwd
}
