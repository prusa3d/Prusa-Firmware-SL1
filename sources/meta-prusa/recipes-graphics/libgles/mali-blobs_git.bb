DESCRIPTION = "libGLES for the A10/A13 Allwinner processor with Mali 400 (X11)"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://EULA%20for%20Mali%20400MP%20_AW.pdf;md5=495406406519c27072a3e6f1e825c0a8"

COMPATIBLE_MACHINE = "sun50i"

# These libraries shouldn't get installed in world builds unless something
# explicitly depends upon them.
EXCLUDE_FROM_WORLD = "1"
PROVIDES = "virtual/libgles1 virtual/libgles2 virtual/egl virtual/mesa virtual/libgbm"

DEBIAN_NOAUTONAME_${PN} = "1"
DEBIAN_NOAUTONAME_${PN}-dev = "1"
DEBIAN_NOAUTONAME_${PN}-dbg = "1"

SRCREV_pn-${PN} = "418f55585e76f375792dbebb3e97532f0c1c556d"
SRC_URI = " \
	gitsm://github.com/bootlin/mali-blobs.git \
	file://0001-eglplatform-h.patch \
"

S = "${WORKDIR}/git"

DEPENDS = "libdrm wayland patchelf-native"
RDEPENDS_${PN} += "kernel-module-mali"

# Inhibit warnings about files being stripped, we can't do anything about it.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

# These are closed binaries generated elsewhere so don't check ldflags & text relocations
INSANE_SKIP_${PN} = "dev-so ldflags textrel"
INSANE_SKIP_${PN}-test = "dev-so ldflags textrel"

FILES_${PN}-dev += "${libdir}/.libMali.so.6~"

do_configure[noexec] = "1"

do_install() {
	# install headers
	for dir in . EGL GLES GLES2 KHR; do
		install -d -m 0755 ${D}${includedir}/${dir}
		install -m 0644 ${S}/include/wayland/${dir}/*.h ${D}${includedir}/${dir}/
	done

	install -d -m 0755 ${D}${libdir}
	install -d -m 0755 ${D}${libdir}/pkgconfig

	install -m 0755 ${S}/r6p2/arm64/wayland/libMali.so ${D}${libdir}/libMali.so.6
	ln -sf libMali.so.6 ${D}${libdir}/libMali.so

	libs="EGL:1.4 GLESv1_CM:1.1 GLESv2:2.0 gbm:1.0"
	for lib in $libs; do
		set -- `echo $lib | tr ':' ' '`

		name=$1
		lower=`echo $name | tr '[:upper:]' '[:lower:]'`
		version=$2
		major=${version%.*}

		ln -sf libMali.so.6 ${D}${libdir}/lib${name}.so.${major}
		ln -sf lib${name}.so.${major} ${D}${libdir}/lib${name}.so.${version}
		ln -sf lib${name}.so.${major} ${D}${libdir}/lib${name}.so

		cat > ${D}${libdir}/pkgconfig/${lower}.pc <<EOF
prefix=${prefix}
includedir=${includedir}
libdir=${libdir}

Name: ${lower}
Description: sunxi-mali
Version: ${version}
Cflags: -I${includedir}
Libs: -L${libdir} -lMali
Libs.private: -ldl
EOF
		ln -sf ${lower}.pc ${D}${libdir}/pkgconfig/lib${lower}.pc
	done

	# Provide fake libffi.so.6
	${CC} -shared -Wl,-soname,libffi.so.6 -o ${D}${libdir}/libffi.so.6.4.0
	ln -s libffi.so.6.4.0 ${D}${libdir}/libffi.so.6
}

patch_installed_lib() {
	soname=libMali.so.6
	so=${D}${libdir}/${soname}
	bkp=${D}${libdir}/.${soname}~

	echo "Backing ${soname} up in ${bkp} ..."
	cp ${so} ${bkp}
	echo "Patching SONAME of ${so} ..."
	patchelf --set-soname ${soname} ${so}
}

restore_vanilla_lib_for_ipk() {
	soname=libMali.so.6
	so=${PKGDEST}/${PN}${base_prefix}${libdir}/${soname}
	bkp=${PKGDEST}/${PN}-dev${base_prefix}${libdir}/.${soname}~

	echo "Restoring original copy of ${soname} in ${so} ..."
        cp ${bkp} ${so}
}

do_install[postfuncs] += "patch_installed_lib"
do_package_write_ipk[prefuncs] += "restore_vanilla_lib_for_ipk"
