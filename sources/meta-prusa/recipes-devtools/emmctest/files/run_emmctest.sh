#!/bin/bash

set -e

PASS=1
while true; do
    if ! badblocks -wvs -b 16384 -t random /dev/mmcblk2; then
        break;
    fi;

    echo "pass: ${PASS}. No problem found."
    echo "cpu temp: $(cat /sys/devices/virtual/thermal/thermal_zone0/temp | sed -r 's/(.{2})(.{3})/\1\.\2/') °C"
    PASS=$((PASS+1))
done;
