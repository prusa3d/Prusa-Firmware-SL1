IMAGE_INSTALL = "\
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

LICENSE = "MIT"

DEPENDS += "systemd-systemctl-native"
rootfs_disable_ssh () {
        systemctl --root=$D disable sshd.socket
}
ROOTFS_POSTPROCESS_COMMAND += "rootfs_disable_ssh ; "

inherit core-image

IMAGE_NAME_SUFFIX = ""
