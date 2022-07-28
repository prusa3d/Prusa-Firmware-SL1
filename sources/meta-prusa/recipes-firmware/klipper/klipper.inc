DESCRIPTION = "Klipper 3D Printing Software"
HOMEPAGE = "https://www.klipper3d.org/"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = " \
        git://git@gitlab.com/prusa3d/sl1/klipper-i2s.git;protocol=ssh;nobranch=1 \
"
SRCREV = "f36041a9925b71b3e08205e7272a9231d88d6501"
