#!/bin/bash

set -e

if [ $# -eq 0 ]
  then
    echo "RAM size is required as argument. Ex: ./run_memtest.sh 800M"
fi

PASS=1
while true; do
    if ! memtester "$1" 1; then
        break;
    fi;

    echo "pass: ${PASS}. No problem found."
    echo "cpu temp: $(cat /sys/devices/virtual/thermal/thermal_zone0/temp | sed -r 's/(.{2})(.{3})/\1\.\2/') °C"
    PASS=$((PASS+1))
done;
