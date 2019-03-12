DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

TZDATA = "\
	tzdata tzdata-europe tzdata-americas tzdata-posix \
"

RDEPENDS_${PN} = "\
	findutils iproute2 iproute2-ss crda  gzip \
	ncurses-tools readline time iputils-ping \
	curl vim nano hwclock haveged \
	bash util-linux-blkid \
	${TZDATA} \
	rauc systemd-journal-gatewayd powerpanic\
"
