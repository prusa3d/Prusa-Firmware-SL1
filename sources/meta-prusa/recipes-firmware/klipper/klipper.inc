DESCRIPTION = "Klipper 3D Printing Software"
HOMEPAGE = "https://www.klipper3d.org/"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = " \
        git://git@gitlab.com/prusa3d/sl1/klipper-i2s.git;protocol=ssh;nobranch=1 \
"
SRCREV = "1bb5cb6f232d72f1d19c96be10e72d13ac92bb14"
