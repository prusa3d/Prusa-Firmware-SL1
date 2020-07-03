DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_packagegroup-prusa-fs = "\
	fuse3 \
	sshfs-fuse \
	cifs-utils \
	davfs2 \
"
