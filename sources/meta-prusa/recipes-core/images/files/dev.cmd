if env exists panel_det_ready
then
else
	echo "U-boot environment requires an update."
	env default bootcmd_prusa
	env default edid_bootarg
	setenv panel_det_ready 1
	saveenv
	echo "Update done, resetting CPU."
	reset
fi
