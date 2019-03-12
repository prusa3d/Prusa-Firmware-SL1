inherit populate_sdk_qt5
IMAGE_INSTALL = "\
	packagegroup-prusa-base \
	packagegroup-prusa-tools \
	packagegroup-prusa-devel \
	packagegroup-prusa-sla \
	packagegroup-prusa-framebuffer \
"

IMAGE_FEATURES += "\
	package-management \
	ssh-server-openssh \
	tools-debug tools-sdk \
	dev-pkgs \
	allow-empty-password \
	empty-root-password \
	post-install-logging \
"

IMAGE_LINGUAS = "en-us"
IMAGE_FSTYPES = "wic"
WIC_CREATE_EXTRA_ARGS = "--no-fstab-update"
WKS_FILE_DEPENDS = "virtual/bootloader e2fsprogs-native bmap-tools-native"
WKS_FILE = "sunxi-sd.wks"

SDKIMAGE_FEATURES = "dev-pkgs dbg-pkgs doc-pkgs staticdev-pkgs qt-pkgs\
"

TOOLCHAIN_HOST_TASK_append = "\
	nativesdk-qtdeclarative \
"

LICENSE = "MIT"

DEPENDS += "systemd-systemctl-native"

rootfs_enable_ssh () {
	systemctl --root=$D enable ssh.socket
}
ROOTFS_POSTPROCESS_COMMAND += "rootfs_enable_ssh ; "

inherit core-image

