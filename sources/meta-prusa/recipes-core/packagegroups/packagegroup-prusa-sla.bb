DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:packagegroup-prusa-sla = "\
	etc slafw touch-ui \
	remote-api-link filemanager \
	prusa-link  weston \
	"

