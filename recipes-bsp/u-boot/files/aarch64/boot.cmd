# Default to (primary) SD
rootdev=mmcblk0p2
if itest.b *0x10028 == 0x02 ; then
	# U-Boot loaded from eMMC or secondary SD so use it for rootfs too
	echo "U-boot loaded from eMMC or secondary SD"
	rootdev=mmcblk2p2
fi
setenv bootargs console=${console} console=tty1 root=/dev/${rootdev} rootwait panic=10 video=HDMI-A-1:D drm.edid_firmware=HDMI-A-1:edid/ls055r1sx04_148.5mhz.bin ${extra}

if fatsize mmc ${mmc_bootdev}:1 update_over_otg
then
	echo "Update over OTG - exposing boot media as a mass storage device"
	ums 0 mmc ${mmc_bootdev}
fi

load mmc 0:1 ${fdt_addr_r} ${fdtfile}
load mmc 0:1 ${kernel_addr_r} Image
booti ${kernel_addr_r} - ${fdt_addr_r}
