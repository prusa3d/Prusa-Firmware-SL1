LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://linux/license/gpl/mali_kernel_license.h;md5=1436c0d104589824163a3eb50fbb5050"

SRC_URI = "git://git@gitlab.com/prusa3d/distro/kernel-module-mali.git;protocol=ssh"

PV = "6.2+git${SRCPV}"
SRCREV = "14f31c4cf46e28b493b8cb86d7287d24d11f7b94"

S = "${WORKDIR}/git"

inherit module

MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE += " \
	KDIR=${STAGING_KERNEL_BUILDDIR} ARCH=arm64 BUILD=release MALI_PLATFORM=sunxi \
	USING_UMP=0 USING_PROFILING=0 USING_DVFS=1 USING_DEVFREQ=1 \
"
