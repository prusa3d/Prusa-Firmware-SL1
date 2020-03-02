DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_${PN} = "\
	haveged nano powerpanic rauc readline \
	tzdata emergencyupdate \
	networkmanager go-omaha \
"
