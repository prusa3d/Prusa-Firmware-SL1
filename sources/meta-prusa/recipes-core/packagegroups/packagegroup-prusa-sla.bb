DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:packagegroup-prusa-sla = "\
	slafw touch-ui \
	prusa-link remote-api-link prusa-connect prusa-connect-sdk \
	filemanager weston \
	emergencyupdate update-selector \
	rauc go-omaha firstboot powerpanic \
	model-detect \
	"

