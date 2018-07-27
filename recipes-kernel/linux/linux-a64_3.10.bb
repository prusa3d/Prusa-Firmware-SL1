SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel

require recipes-kernel/linux/linux.inc

DESCRIPTION = "Linux kernel for Allwinner a64 processor"

COMPATIBLE_MACHINE = "sun50i"

PV = "3.10.104"
PR = "r1"
SRCREV_pn-${PN} = "065d46d2d10b774f561ed7f570b422af43978dea"

DEPENDS_append = " libgcc"

MACHINE_KERNEL_PR_append = "a"
KBRANCH = "master"

SRC_URI += "git://github.com/zavorka/linux-a64.git;branch=master;protocol=git \
        file://defconfig \
        "

S = "${WORKDIR}/git"

#fix QA issue "Files/directories were installed but not shipped: /usr/src/debug"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
