LICENSE = "MIT"

inherit core-image populate_sdk_qt5

IMAGE_INSTALL += "\
	packagegroup-prusa-base \
	packagegroup-prusa-tools \
	packagegroup-prusa-devel \
	packagegroup-prusa-fs \
	packagegroup-prusa-sla \
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

IMAGE_FSTYPES = "wic wic.bmap"
WIC_CREATE_EXTRA_ARGS = "--no-fstab-update"
WKS_FILE_DEPENDS = "virtual/bootloader e2fsprogs-native bmap-tools-native gptfdisk-native"
WKS_FILE = "sunxi-sd.wks"

SDKIMAGE_FEATURES = "dev-pkgs dbg-pkgs doc-pkgs staticdev-pkgs qt-pkgs\
"

TOOLCHAIN_TARGET_TASK_append = "\
	networkmanager-qt-dev \
	extra-cmake-modules-dev \
"
TOOLCHAIN_HOST_TASK_append = "\
        nativesdk-qtdeclarative-tools \
        nativesdk-python3 \
        nativesdk-python3-core \
        nativesdk-python3-pickle \
        nativesdk-python3-logging \
        nativesdk-python3-compile \
        nativesdk-python3-fcntl \
        nativesdk-python3-shell \
        nativesdk-python3-misc \
        nativesdk-python3-multiprocessing \
        nativesdk-python3-distutils \
        nativesdk-python3-compression \
        nativesdk-python3-json \
        nativesdk-python3-unittest \
        nativesdk-python3-mmap \
        nativesdk-python3-git \
        nativesdk-python3-pkgutil \
        nativesdk-python3-pyyaml \
"



DEPENDS += "systemd-systemctl-native coreutils-native u-boot-tools-native"

rootfs_set_api_key () {
	user=maker
	realm=prusa-sl1
	key=developer
	dir=$D${sysconfdir}/sl1fw

	install -d ${dir}

	echo ${key} > ${dir}/api.key
	echo -n ${key} > ${dir}/slicer-upload-api.key

	hash=$(echo -n "${user}:${realm}:${key}" | md5sum | sed 's/  -$//')
	echo "${user}:${realm}:${hash}" > ${dir}/htdigest.passwd

	systemctl --root=$D mask api-keygen.service
}

rootfs_enable_ssh_and_serial_unconditionally () {
	systemctl --root=$D enable sshd.socket
	rm $D${systemd_system_unitdir}/serial-getty@ttyS0.service.d/condition-enabled.conf
	rm $D${systemd_system_unitdir}/sshd.socket.d/condition-enabled.conf
}

UBOOT_DEV_CMD := "${THISDIR}/files/dev.cmd"

rootfs_insert_boot_scr () {
	mkimage -C none -A arm -T script -d ${UBOOT_DEV_CMD} $D/boot.scr
}

ROOTFS_POSTPROCESS_COMMAND += " \
	rootfs_set_api_key ; rootfs_insert_boot_scr ; \
	rootfs_enable_ssh_and_serial_unconditionally ; "
IMAGE_NAME = "${IMAGE_BASENAME}-${MACHINE}-${DISTRO_VERSION}${IMAGE_VERSION_SUFFIX}"

