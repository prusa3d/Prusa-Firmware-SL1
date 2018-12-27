# Default to (primary) SD
rootdev=mmcblk0p2
bootdev=mmcblk0p1
if itest.b *0x10028 == 0x02 || itest.b *0x10028 == 0x12; then
	# U-Boot loaded from eMMC so use it for rootfs too
	echo "U-boot loaded from eMMC"
	rootdev=mmcblk2p2
	bootdev=mmcblk2p1
fi
setenv bootargs console=${console} root=/dev/${rootdev} rootwait panic=10 video=HDMI-A-1:D drm.edid_firmware=HDMI-A-1:edid/ls055r1sx04_148.5mhz.bin ${extra} logo.nologo vt.global_cursor_default=0 systemd.setenv=BOOT_DEV=/dev/${bootdev}

echo "Update over OTG - exposing boot media as a mass storage device"
ums 0 mmc ${mmc_bootdev}

load mmc ${mmc_bootdev}:1 ${fdt_addr_r} ${fdtfile}
load mmc ${mmc_bootdev}:1 ${kernel_addr_r} Image
booti ${kernel_addr_r} - ${fdt_addr_r}
