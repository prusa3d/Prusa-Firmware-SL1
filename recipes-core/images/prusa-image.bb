IMAGE_INSTALL = "\
	packagegroup-core-boot packagegroup-self-hosted \
	packagegroup-prusa-base packagegroup-qt5-toolchain-target \
	kernel-dev kernel-devsrc connman connman-plugin-ethernet "

IMAGE_FEATURES += "\
	dev-pkgs x11 \
	package-management \
	ssh-server-openssh \
	tools-debug tools-sdk \
	nfs-server nfs-client \
	allow-empty-password \
	empty-root-password \
	post-install-logging \
"

IMAGE_LINGUAS = "en_US"

SDKIMAGE_FEATURES = "dev-pkgs dbg-pkgs doc-pkgs staticdev-pkgs perf "

LICENSE = "MIT"

inherit core-image
