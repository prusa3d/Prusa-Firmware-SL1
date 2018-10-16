DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

# Do include qtwebkit !
RDEPENDS_packagegroup-prusa-qt = "\
    qtquickcontrols-qmlplugins qttools-plugins \
    qtquickcontrols2\
    qtquickcontrols2-dev\
    qtquickcontrols\
    qtquickcontrols-dev\
    qtsvg\
    qtwebsockets-qmlplugins\
    qtwebsockets\
"
# qtwebengine

