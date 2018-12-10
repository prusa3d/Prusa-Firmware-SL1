# Default to (primary) SD
rootdev=mmcblk0p2
if itest.b *0x28 == 0x02 ; then
	# U-Boot loaded from eMMC or secondary SD so use it for rootfs too
	echo "U-boot loaded from eMMC or secondary SD"
	rootdev=mmcblk2p2
fi
setenv bootargs console=${console} console=tty1 root=/dev/${rootdev} rootwait panic=10 drm.debug=0xf video=HDMI-A-1:D drm.edid_firmware=HDMI-A-1:edid/ls055r1sx04_148.5mhz.bin ${extra}
load mmc 0:1 ${fdt_addr_r} ${fdtfile}
load mmc 0:1 ${kernel_addr_r} Image
booti ${kernel_addr_r} - ${fdt_addr_r}
