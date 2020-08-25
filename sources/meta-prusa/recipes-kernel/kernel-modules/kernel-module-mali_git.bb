LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://linux/license/gpl/mali_kernel_license.h;md5=1436c0d104589824163a3eb50fbb5050"

SRC_URI = " \
	git://git@gitlab.com/prusa3d/distro/kernel-module-mali.git;protocol=ssh \
	file://0023-mali-support-building-against-5.7.patch \
"

PV = "6.2+git${SRCPV}"
SRCREV = "b89720f24bc6ae096ea807af314f7c72c9c4deee"

S = "${WORKDIR}/git"

inherit module

MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE += " \
	KDIR=${STAGING_KERNEL_BUILDDIR} ARCH=arm64 BUILD=release MALI_PLATFORM=sunxi	\
	USING_UMP=0 USING_PROFILING=0 USING_DVFS=0 USING_DEVFREQ=0 USING_DT=0		\
	USING_GPU_UTILIZATION=1 \
"
