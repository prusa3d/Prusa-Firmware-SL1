DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

OPENSSH = "\
    openssh-scp openssh-ssh openssh-sftp openssh-sftp-server \
    openssh-sshd openssh-keygen packagegroup-core-ssh-openssh \
"

TZDATA = "\
	tzdata tzdata-europe tzdata-americas tzdata-posix \
"

RDEPENDS_${PN} = "\
	readline time iputils-ping dhcp-client curl vim nano \
	bash \
	kernel-modules iw wpa-supplicant linux-firmware-bcm43362 \
	${OPENSSH} ${TZDATA} \
	mpv root-ssh-keys haveged mali-blobs\
"
