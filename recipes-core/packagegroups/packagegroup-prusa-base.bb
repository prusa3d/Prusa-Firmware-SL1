DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

#
# Set by the machine configuration with packages essential for device bootup
#
MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""

OPENSSH = "\
    openssh-scp openssh-ssh openssh-sftp openssh-sftp-server \
    openssh-sshd openssh-keygen packagegroup-core-ssh-openssh \
"

TZDATA = "\
	tzdata tzdata-europe tzdata-americas tzdata-posix \
"

RDEPENDS_${PN} = "\
	base-files base-passwd netbase \
	${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
	readline time iputils-ping dhcp-client curl vim-tiny nano \
	bash \
	kernel-modules iw wpa-supplicant linux-firmware-bcm43362 \
	${OPENSSH} ${TZDATA} \
	haveged mali-blobs hwclock \
	rauc \
"

RRECOMMENDS_${PN} = ${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
