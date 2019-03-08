SUMMARY = "Extremely basic live image init script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

RDEPENDS_${PN} = "${VIRTUAL-RUNTIME_base-utils}"

#PR = "r2"

SRC_URI = "file://init-ab-slots.sh"
S = "${WORKDIR}"

do_install() {
        install -m 0755 ${WORKDIR}/init-ab-slots.sh ${D}/init

        # Create device nodes expected by some kernels in initramfs
        # before even executing /init.
        install -d ${D}/dev
        mknod -m 622 ${D}/dev/console c 5 1
        install -d ${D}/proc
        install -d ${D}/sys
}

inherit allarch

FILES_${PN} += "/init /dev/console /proc /sys"
