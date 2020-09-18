SUMMARY = "DBus plugin for Nemo Mobile"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD;md5=3775480a712fc46a69647678acb234cb"
SRC_URI = "git://git.merproject.org/mer-core/nemo-qml-plugin-dbus.git;protocol=https\
	   "
PV = "2.1.16+git${SRCPV}"
SRCREV = "861d8e15f05bc434bb475967c48acca6473b27cb"

S = "${WORKDIR}/git"
FILES_${PN} = "\
  /usr/lib/qml/Nemo/DBus/libnemodbus.so \
  /usr/lib/qml/Nemo/DBus/qmldir \
  /usr/lib/qml/Nemo/DBus/plugins.qmltypes \
  /usr/lib/libnemodbus.so.1.0.0 \
  /usr/lib/libnemodbus.so.1 \
  /usr/lib/libnemodbus.so.1.0 \
"

do_install_append () {
	rm ${D}/opt -rf
}


DEPENDS = "qtdeclarative"
inherit qmake5

