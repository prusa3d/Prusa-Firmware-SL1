if env exists panel_det_ready
then
else
	echo "U-boot environment requires an update."
	env default bootcmd_prusa
	setenv edid_bootarg 'drm.edid_firmware=HDMI-A-1:edid/ls055r1sx04_148.5mhz.bin'
	setenv panel_det_ready 1
	saveenv
	echo "Update done, resetting CPU."
	reset
fi
