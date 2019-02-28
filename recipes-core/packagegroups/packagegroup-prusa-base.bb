DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

#
# packages essential for device bootup
#
MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""
DISTRO_ESSENTIAL_EXTRA_RDEPENDS ?= ""
DISTRO_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""

OPENSSH = "\
    openssh-scp openssh-ssh openssh-sftp openssh-sftp-server \
    openssh-sshd openssh-keygen packagegroup-core-ssh-openssh \
"

TZDATA = "\
	tzdata tzdata-europe tzdata-americas tzdata-posix \
"

DEPENDS_${PN} = "\
	${MACHINE_EXTRA_DEPENDS} \
	${DISTRO_EXTRA_DEPENDS} \
"

RDEPENDS_${PN} = "\
	${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
	${MACHINE_EXTRA_RDEPENDS} \
	${DISTRO_ESSENTIAL_EXTRA_RDEPENDS} \
	${DISTRO_EXTRA_RDEPENDS} \
	${VIRTUAL-RUNTIME_base-utils} \
	${VIRTUAL-RUNTIME_login_manager} \
	${VIRTUAL-RUNTIME_init_manager} \
	${VIRTUAL-RUNTIME_dev_manager} \
	${VIRTUAL-RUNTIME_update-alternatives} \
	base-files base-passwd netbase findutils \
	keymaps lrzsz setserial iproute2 gzip \
	readline time iputils-ping dhcp-client curl vim-tiny nano \
	bash e2fsprogs-mke2fs dosfstools util-linux-blkid \
	kernel-modules iw wpa-supplicant linux-firmware-bcm43362 \
	${OPENSSH} ${TZDATA} \
	haveged mali-blobs hwclock \
	rauc systemd-journal-gatewayd powerpanic\
"

RRECOMMENDS_${PN} = "\
	${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
        ${MACHINE_EXTRA_RRECOMMENDS} \
        ${DISTRO_ESSENTIAL_EXTRA_RRECOMMENDS} \
        ${DISTRO_EXTRA_RRECOMMENDS} \
"
