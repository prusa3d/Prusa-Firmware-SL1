DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_packagegroup-prusa-sla = "nginx python rsync \
	sla-edid etc zsh samba python-pip sl1fw sla-client sla-splash sla-slicer-upload\
	"

