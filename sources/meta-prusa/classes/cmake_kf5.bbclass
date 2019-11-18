inherit cmake_qt5
 
EXTRA_OECMAKE_class-native += " \
    -DOE_KF5_PATH_HOST_ROOT=${STAGING_DIR_HOST} \
    -DBUILD_TESTING=OFF \
"
 
EXTRA_OECMAKE_class-target += " \
    -DOE_KF5_PATH_HOST_ROOT=${STAGING_DIR_HOST} \
    -DKDE_INSTALL_USE_QT_SYS_PATHS=ON \
    -DBUILD_TESTING=OFF \
    -DKF5_HOST_TOOLING=${STAGING_DIR_NATIVE}/${libdir}/cmake \
"

DEPENDS += "extra-cmake-modules qttools-native"

# don't bother with translations for host tools
do_configure_prepend_class-native() {
    rm -rf ${S}/po
}

do_compile_prepend() {
     export XDG_DATA_DIRS=${STAGING_DATADIR}:$XDG_DATA_DIRS
     export LD_LIBRARY_PATH=${STAGING_LIBDIR_NATIVE}:$LD_LIBRARY_PATH
}

# This function is rather offensive right now, but it seems to work
do_install_prepend() {
    if [ "0" -ne $(find . -name \*.cmake | grep _usr | wc -l) ]; then
        sed -i 's/\"\/usr\//\"\$\{OE_KF5_PATH_HOST_ROOT\}\/usr\//g' $(find . -name "*.cmake" | grep _usr)
        sed -i 's/\;\/usr\//\;\$\{OE_KF5_PATH_HOST_ROOT\}\/usr\//g' $(find . -name "*.cmake" | grep _usr)
    fi
}

BBCLASSEXTEND = "native nativesdk"

FILES_${PN} += " \
    ${datadir}/dbus-1/services/*.service \
    ${datadir}/dbus-1/system-services/*.service \
    ${datadir}/dbus-1/system.d/*.conf \
    ${datadir}/knotifications5/*.notifyrc \
    ${datadir}/kservices5/*.desktop \
    ${datadir}/kservices5/*.protocol \
    ${datadir}/kservicetypes5/*.desktop \
    ${datadir}/kservices5/*.desktop \
    ${datadir}/polkit-1/actions/*.policy \
"
 
FILES_${PN}-dev += " \
    ${datadir}/dbus-1/interfaces/*.xml \
    ${datadir}/kdevappwizard/templates/*.tar.bz2 \
    ${datadir}/qlogging-categories5 \
    ${libdir}/cmake \
    ${prefix}/mkspecs/modules/qt_*.pri \
"
