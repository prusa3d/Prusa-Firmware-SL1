SUMMARY = "v4l2-request libVA Backend"
DESCRIPTION = "\
This libVA backend is designed to work with the Linux Video4Linux2 Request API \
that is used by a number of video codecs drivers, including the Video Engine \
found in most Allwinner SoCs.The runtime library for accessing the kernel \
DRM services."
LICENSE = "LGPLv2.1 | MIT"
LIC_FILES_CHKSUM = "\
	file://COPYING.LGPL;md5=4fbd65380cdd255951079008b364516c \
	file://COPYING.MIT;md5=0fdfbe4b63f3b713a3427cec241e05a4 \
"
DEPENDS = "drm libva"

SRC_URI = "git://github.com/bootlin/${BPN}"
SRCREV = "7f359be748a755b1250f42d3ff6f5729c01bc85a"

UPSTREAM_CHECK_COMMITS = "1"

S = "${WORKDIR}/git"

inherit meson pkgconfig

FILES_${PN} += "${libdir}/dri"
