#!/bin/sh

DIR=/etc/sl1fw
REALM=prusa-sl1
USER=maker

KEY=$(/usr/bin/base64 /dev/random | /usr/bin/head -c 8)

# Store apikey plain
echo ${KEY} > ${DIR}/api.key

# Store apikey for slicer uploader
echo -n ${KEY} > ${DIR}/slicer-upload-api.key

# Generate htdigest access for ${USER} with apikey password
SIGN=$(echo -n "${USER}:${REALM}:${KEY}" | md5sum | sed 's/ .*//')
echo "${USER}:${REALM}:${SIGN}" > ${DIR}/htdigest.passwd



