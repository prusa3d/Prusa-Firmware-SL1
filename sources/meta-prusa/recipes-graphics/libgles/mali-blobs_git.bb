DESCRIPTION = "libGLES for the A10/A13 Allwinner processor with Mali 400 (X11)"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://EULA%20for%20Mali%20400MP%20_AW.pdf;md5=495406406519c27072a3e6f1e825c0a8"

COMPATIBLE_MACHINE = "sun50i"

# These libraries shouldn't get installed in world builds unless something
# explicitly depends upon them.
EXCLUDE_FROM_WORLD = "1"
PROVIDES = "virtual/libgles1 virtual/libgles2 virtual/egl virtual/mesa virtual/libgbm"

SRCREV_pn-${PN} = "418f55585e76f375792dbebb3e97532f0c1c556d"
SRC_URI = " \
	gitsm://github.com/bootlin/mali-blobs.git \
	file://0001-eglplatform-h.patch \
"

S = "${WORKDIR}/git"

DEPENDS = "libdrm wayland"

# Inhibit warnings about files being stripped, we can't do anything about it.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

do_configure[noexec] = "1"

do_install() {
	# install headers
	install -d -m 0755 ${D}${includedir}
	install -m 0644 ${S}/include/wayland/gbm.h ${D}${includedir}/
	install -d -m 0755 ${D}${includedir}/EGL
	install -m 0644 ${S}/include/wayland/EGL/*.h ${D}${includedir}/EGL/
	install -d -m 0755 ${D}${includedir}/GLES
	install -m 0644 ${S}/include/wayland/GLES/*.h ${D}${includedir}/GLES/
	install -d -m 0755 ${D}${includedir}/GLES2
	install -m 0644 ${S}/include/wayland/GLES2/*.h ${D}${includedir}/GLES2/
	install -d -m 0755 ${D}${includedir}/KHR
	install -m 0644 ${S}/include/wayland/KHR/*.h ${D}${includedir}/KHR/

	# Copy the .pc files
	# install -d -m 0755 ${D}${libdir}/pkgconfig
	# install -m 0644 ${S}/egl.pc ${D}${libdir}/pkgconfig/
	# install -m 0644 ${S}/gles_cm.pc ${D}${libdir}/pkgconfig/
	# install -m 0644 ${S}/glesv2.pc ${D}${libdir}/pkgconfig/

	# install -d ${D}${libdir}
	# install -d ${D}${includedir}

	# Fix .so name and create symlinks, binary package provides .so wich can't be included directly in package without triggering the 'dev-so' QA check
	# Packages like xf86-video-fbturbo dlopen() libUMP.so, so we do need to ship the .so files in ${PN}

	install -d -m 0755 ${D}${libdir}
	install -m 0755 ${S}/r6p2/arm64/wayland/libMali.so ${D}${libdir}/libMali.so.6

	ln -sf libMali.so.6 ${D}${libdir}/libMali.so

	for minor in libEGL.so.1.4 libGLESv1_CM.so.1.1 libGLESv2.so.2.0 libgbm.so.1.0 ; do
		major=${minor%.*}
		plain=${major%.*}
		ln -sf libMali.so.6 ${D}${libdir}/${major}
		ln -sf ${major} ${D}${libdir}/${minor}
		ln -sf ${major} ${D}${libdir}/${plain}
	done

	install -d -m 0755 ${D}${libdir}/pkgconfig
	echo "prefix=${prefix}"		>> ${D}${libdir}/pkgconfig/gbm.pc
	echo "includedir=${includedir}"	>> ${D}${libdir}/pkgconfig/gbm.pc
	echo "libdir=${libdir}"		>> ${D}${libdir}/pkgconfig/gbm.pc
	echo ""				>> ${D}${libdir}/pkgconfig/gbm.pc
	echo "Name: gbm"		>> ${D}${libdir}/pkgconfig/gbm.pc
	echo "Description: sunxi-mali"	>> ${D}${libdir}/pkgconfig/gbm.pc
	echo "Version: 1.0"		>> ${D}${libdir}/pkgconfig/gbm.pc
	echo "Cflags: -I${includedir}"	>> ${D}${libdir}/pkgconfig/gbm.pc
	echo "Libs: -L${libdir} -lgbm"	>> ${D}${libdir}/pkgconfig/gbm.pc
	echo "Libs.private: -ldl"	>> ${D}${libdir}/pkgconfig/gbm.pc
	ln -sf gbm.pc ${D}${libdir}/pkgconfig/libgbm.pc

	echo "prefix=${prefix}"		>> ${D}${libdir}/pkgconfig/egl.pc
	echo "includedir=${includedir}"	>> ${D}${libdir}/pkgconfig/egl.pc
	echo "libdir=${libdir}"		>> ${D}${libdir}/pkgconfig/egl.pc
	echo ""				>> ${D}${libdir}/pkgconfig/egl.pc
	echo "Name: egl"		>> ${D}${libdir}/pkgconfig/egl.pc
	echo "Description: sunxi-mali"	>> ${D}${libdir}/pkgconfig/egl.pc
	echo "Version: 1.0"		>> ${D}${libdir}/pkgconfig/egl.pc
	echo "Cflags: -I${includedir}"	>> ${D}${libdir}/pkgconfig/egl.pc
	echo "Libs: -L${libdir} -lEGL"	>> ${D}${libdir}/pkgconfig/egl.pc
	echo "Libs.private: -ldl"	>> ${D}${libdir}/pkgconfig/egl.pc
}

RPROVIDES_${PN} += "libGLESv2.so libEGL.so libGLESv2.so libGLESv1_CM.so libMali.so"
RDEPENDS_${PN} += "kernel-module-mali"

FILES_${PN} += "${libdir}/lib*.so"
FILES_${PN}-dev = "${includedir} ${libdir}/pkgconfig"

# These are closed binaries generated elsewhere so don't check ldflags & text relocations
INSANE_SKIP_${PN} = "dev-so ldflags textrel"
INSANE_SKIP_${PN}-test = "dev-so ldflags textrel"
