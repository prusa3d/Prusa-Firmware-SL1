DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_packagegroup-prusa-sla = "\
	sla-edid etc sl1fw sla-client web-ui\
	sla-splash sla-slicer-upload wifi-config\
	libva-v4l2-request \
	"

