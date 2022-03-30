DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:packagegroup-prusa-sla = "\
	slafw touch-ui \
	remote-api-link filemanager \
	prusa-link  weston \
	emergencyupdate update-selector \
	rauc go-omaha firstboot powerpanic \
	model-detect \
	"

