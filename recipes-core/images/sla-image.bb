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

inherit core-image

IMAGE_NAME_SUFFIX = ""
