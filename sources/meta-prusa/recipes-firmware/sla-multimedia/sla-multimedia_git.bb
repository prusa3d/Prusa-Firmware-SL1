LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0a3fd806d2a5d0aebdca6670aaf6d68f"

SRC_URI = "git://git@gitlab.com/prusa3d/sl1/sla-multimedia.git;protocol=ssh;branch=master"
# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "7bb85ce6e52bee8f8f78d0aca3aedceec625743d"

S = "${WORKDIR}/git"

do_install () {
	install -d "${D}${datadir}/"	
	find sla-multimedia -type f -exec install -m 644 -D {} ${D}${datadir}/{} \;
}

