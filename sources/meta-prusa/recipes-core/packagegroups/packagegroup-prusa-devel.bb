DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:packagegroup-prusa-devel = "\
    cscope ctags cmake gcc quilt m4 meson git binutils ninja \
    gdb patch patchelf elfutils diffutils e2fsprogs-resize2fs \
    evtest parted vim python3-pip zsh gammaray glmark2 tslib \
    libgpiod-tools networkmanager-nmcli memtest tmux emmctest \
"
