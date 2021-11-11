require klipper.inc

SRC_URI += "\
	file://config \
	file://klipper-mcu-flash.py \
	file://klipper-mcu-flash.service \
"

S = "${WORKDIR}/git"

DEPENDS += "gcc-arm-none-eabi-native"
RDEPENDS:${PN} += "python3 python3-gpio stm32flash"

FILES:${PN} += "\
	${base_libdir}/firmware/klipper.bin \
	${bindir}/klipper-mcu-flash.py \
"

inherit systemd

do_configure() {
	cp ${WORKDIR}/config ${S}/.config
}


do_compile() {
	make
}

do_install() {
	# MCU Firmware
	install -d ${D}${base_libdir}/firmware
	install ${S}/out/klipper.bin ${D}${base_libdir}/firmware

	# Flash service
	install -d ${D}${systemd_system_unitdir}/
	install --mode 644 ${WORKDIR}/klipper-mcu-flash.service ${D}${systemd_system_unitdir}/

	# Flash script
	install -d ${D}${bindir}
	install --mode 755 ${WORKDIR}/klipper-mcu-flash.py ${D}${bindir}
}

SYSTEMD_SERVICE:${PN} = "klipper-mcu-flash.service"
