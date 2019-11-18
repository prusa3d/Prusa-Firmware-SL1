# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

SUMMARY = "DBus plugin for Nemo Mobile"
# Unable to find any files that looked like license statements. Check the accompanying
# documentation and source headers and set LICENSE and LIC_FILES_CHKSUM accordingly.
#
# NOTE: LICENSE is being set to "CLOSED" to allow you to at least start building - if
# this is not accurate with respect to the licensing of the software being built (it
# will not be in most cases) you must specify the correct value before using this
# recipe for anything other than initial testing/development!
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD;md5=3775480a712fc46a69647678acb234cb"
#FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI = "git://git.merproject.org/mer-core/nemo-qml-plugin-dbus.git;protocol=https\
	   "

# Modify these as desired
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
#  /opt/tests \
#  /opt/tests/nemo-qml-plugins-qt5 \
#  /opt/tests/nemo-qml-plugins-qt5/dbus \
#  /opt/tests/nemo-qml-plugins-qt5/dbus/test-definition \
#  /opt/tests/nemo-qml-plugins-qt5/dbus/auto \
#  /opt/tests/nemo-qml-plugins-qt5/dbus/test-definition/tests.xml \
#  /opt/tests/nemo-qml-plugins-qt5/dbus/auto/tst_dbus.qml \
#  /opt/tests/nemo-qml-plugins-qt5/dbus/auto/tst_dbus_interface.qml \
#"

do_install_append () {
	rm ${D}/opt/tests -rf
        
	
	#install -d ${D}${systemd_system_unitdir}/
        #install --mode 644 ${WORKDIR}/sla-client.service ${D}${systemd_system_unitdir}/

        #install -d ${D}/usr/share/
        #install --mode 644 ${WORKDIR}/sla-client-config.json ${D}/usr/share/

        #install -d ${D}//etc/dbus-1/system.d
        #install --mode 644 ${WORKDIR}/cz.prusa3d.sl1.notificationsink.conf ${D}/etc/dbus-1/system.d/
}


DEPENDS = "qtdeclarative"
# NOTE: spec file indicates the license may be "LGPLv2.1"
inherit qmake5

