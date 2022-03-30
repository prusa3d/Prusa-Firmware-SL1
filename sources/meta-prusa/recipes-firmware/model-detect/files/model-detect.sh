#!/bin/sh

EXPO_PANEL_PATH=/sys/bus/i2c/devices/1-000f/of_node
M1_FILE=/usr/share/factory/defaults/printer_m1_enabled
RUN_PATH=/run/model
EXPO_PANEL_NAME=$(tr -d '\0' < "$EXPO_PANEL_PATH"/panel-name)
PRINTER_MODEL=""

case "$EXPO_PANEL_NAME" in
    "rv059fbb")
        if [ -f "$M1_FILE" ]; then
                PRINTER_MODEL="m1"
        else
                PRINTER_MODEL="sl1s"  # No model has been set in factory. Maybe firstboot?
        fi
        ;;
    "ls055r1sx04")
        PRINTER_MODEL="sl1"
        ;;
    *)
        echo "Unknown printer model"
        exit 19
        ;;
esac

echo "$PRINTER_MODEL"  # print current detected model
rm -rf "$RUN_PATH"  # if model folder exists from some strange reason, delete it

# create model folder and file
mkdir -m 444 "$RUN_PATH"
touch "$RUN_PATH/$PRINTER_MODEL"