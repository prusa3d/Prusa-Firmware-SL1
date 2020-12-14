inherit pkgconfig pypi setuptools3

SUMMARY = "Python bindings for the libwayland library written in pure Python"
DESCRIPTION = "PyWayland provides a wrapper to the libwayland library using the CFFI library to provide access to the Wayland library calls and written in pure Python."

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=ac62efbc3bc8685a527fe4a3abc1359c"

SRC_URI += " file://0001-fix-wayland-xml-access.patch"
SRC_URI[md5sum] = "4746d4360d81597c01ca57bf9903bda8"
SRC_URI[sha256sum] = "b1edef7ba733998ca3cfa849ab52ece6d33a9a0963b2fd71fe0937ab6bcd528d"

DEPENDS += "wayland wayland-native python3-cffi python3-setuptools-native python3-pip-native"

