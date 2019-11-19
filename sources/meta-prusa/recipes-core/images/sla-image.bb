LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL += "\
	packagegroup-prusa-base \
	packagegroup-prusa-sla \
	packagegroup-prusa-tools \
"

IMAGE_FEATURES += "\
	ssh-server-openssh \
	allow-empty-password \
	empty-root-password \
	post-install-logging \
"
IMAGE_FEATURES_remove = "package-management"

IMAGE_LINGUAS = ""

DEPENDS += "systemd-systemctl-native"

rootfs_disable_ssh () {
	systemctl --root=$D mask sshd.socket
}

rootfs_disable_serial () {
	systemctl --root=$D mask serial-getty@ttyS0.service
}

rootfs_disable_terminal () {
	systemctl --root=$D mask getty@tty1.service
}

ROOTFS_POSTPROCESS_COMMAND += "rootfs_disable_ssh ; rootfs_disable_serial ; rootfs_disable_terminal ; "

IMAGEE_NAME_SUFFIX = ""

IMAGE_FSTYPES = "factory etc root"
