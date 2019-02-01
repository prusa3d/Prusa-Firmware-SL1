#!/bin/sh

DIR=/etc/sl1fw
REALM=prusa-sl1
USER=maker

while true; do
	KEY=$(/usr/bin/base64 /dev/random | /usr/bin/head -c 8)
	if echo ${KEY} | grep --quiet -v -e O -e 0 -e l -e I; then
		break;
	fi;
done

# Store apikey plain
echo ${KEY} > ${DIR}/api.key

# Store apikey for slicer uploader
echo -n ${KEY} > ${DIR}/slicer-upload-api.key

# Generate htdigest access for ${USER} with apikey password
SIGN=$(echo -n "${USER}:${REALM}:${KEY}" | md5sum | sed 's/ .*//')
echo "${USER}:${REALM}:${SIGN}" > ${DIR}/htdigest.passwd



