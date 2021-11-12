# Copyright (C) 2019 Garmin Ltd. or its subsidaries
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Baremetal GCC for ARM-R and ARM-M processors"
LICENSE = "GPL-3.0-with-GCC-exception & GPLv3"

LIC_FILES_CHKSUM = "file://share/doc/gcc-arm-none-eabi/license.txt;md5=c18349634b740b7b95f2c2159af888f5"

PROVIDES = "virtual/arm-none-eabi-gcc"

SRC_URI = "https://developer.arm.com/-/media/Files/downloads/gnu-rm/${PV}/gcc-arm-none-eabi-${PV}-x86_64-linux.tar.bz2"
#SRC_URI = "https://developer.arm.com/-/media/Files/downloads/gnu-rm/9-2019q4/RC2.1/${BPN}-${PV}-x86_64-linux.tar.bz2"

SRC_URI[md5sum] = "2383e4eb4ea23f248d33adc70dc3227e"
SRC_URI[sha256sum] = "97dbb4f019ad1650b732faffcc881689cedc14e2b7ee863d390e0a41ef16c9a3"

S = "${WORKDIR}/${BPN}-${PV}"

inherit native

COMPATIBLE_HOST = "x86_64.*-linux"

do_install() {
    install -d ${D}${datadir}/arm-none-eabi/
    cp -r ${S}/. ${D}${datadir}/arm-none-eabi/

    install -d ${D}${bindir}
    # Symlink all executables into bindir
    for f in ${D}${datadir}/arm-none-eabi/bin/arm-none-eabi-*; do
        lnr $f ${D}${bindir}/$(basename $f)
    done
}

INSANE_SKIP:${PN} = "already-stripped"

INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_STRIP = "1"
NHIBIT_PACKAGE_DEBUG_SPLIT = "1"
