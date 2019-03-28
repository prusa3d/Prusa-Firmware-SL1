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
ROOTFS_POSTPROCESS_COMMAND += "rootfs_disable_ssh ; "

IMAGE_NAME_SUFFIX = ""
