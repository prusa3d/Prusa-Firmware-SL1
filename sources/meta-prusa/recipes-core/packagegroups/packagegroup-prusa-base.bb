DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
	haveged nano powerpanic rauc readline \
	tzdata emergencyupdate update-selector \
	networkmanager go-omaha firstboot\
"
