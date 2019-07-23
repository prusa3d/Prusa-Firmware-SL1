SRC_URI = "git://git@gitlab.com/prusa3d/distro/u-boot.git;protocol=ssh;branch=master"
SRCREV = "5353bc63f8b9dcfb5a9af02d680bdabdac336d3a"

EXTRA_OEMAKE_class-native = 'HOSTCC="${CC} ${CFLAGS} ${LDFLAGS}" V=1'

do_install_class-native () {
        install -d ${D}${bindir}
        install -m 755 ${S}/tools/env/fw_printenv ${D}${bindir}/fw_printenv
        install -m 755 ${S}/tools/env/fw_printenv ${D}${bindir}/fw_setenv
}

BBCLASSEXTEND += " native"
