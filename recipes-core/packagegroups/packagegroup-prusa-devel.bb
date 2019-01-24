DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_packagegroup-prusa-devel = "\
    cscope ctags cmake gcc quilt m4 meson git binutils ninja \
    gdb patch patchelf elfutils diffutils \
    evtest parted vim python-pip"
