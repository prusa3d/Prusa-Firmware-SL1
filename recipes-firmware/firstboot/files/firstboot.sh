#!/bin/sh

logger "starting first boot script"

hwclock
hwclock -w
hwclock
logger "first boot script work done"

#job done, remove it from systemd services
systemctl disable firstboot.service

logger "first boot script disabled"
