setenv bootargs_old rootwait panic=10 video=HDMI-A-1:D drm.edid_firmware=HDMI-A-1:edid/ls055r1sx04_148.5mhz.bin vt.global_cursor_default=0 console=${console} board_revision=${board_revision} stmmac.quirks=${eth_quirks} root=/dev/mmcblk0p1

if test "${bootargs}" = "${bootargs_old}"
then
	env delete panel_det_ready
fi
env delete bootargs_old

if env exists panel_det_ready
then
else
	echo "U-boot environment requires an update."
	echo "Appending ${edid_bootarg} to bootargs"
	setenv bootargs ${bootargs} ${edid_bootarg}
	env default bootcmd_prusa
	env default edid_bootarg
	setenv panel_det_ready 1
	saveenv
	echo "Update done, continuing."
fi
