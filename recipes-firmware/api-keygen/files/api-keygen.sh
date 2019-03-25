#!/bin/sh

DIR=/etc/sl1fw
REALM=prusa-sl1
USER=maker

if [ -n "${1}" ]; then
	KEY=${1}
	
	if [ $(echo -n ${KEY} | wc -c) -lt 8 ]; then
		exit -1;
	fi;
	
else
	while true; do
		KEY=$(/usr/bin/base64 /dev/random | /usr/bin/head -c 8)
		if echo ${KEY} | grep -q -v -e O -e 0 -e l -e I -e \\\\ -e /; then
			break;
		fi;
	done
fi;

# Store apikey plain
echo ${KEY} > ${DIR}/api.key

# Store apikey for slicer uploader
echo -n ${KEY} > ${DIR}/slicer-upload-api.key

# Generate htdigest access for ${USER} with apikey password
SIGN=$(echo -n "${USER}:${REALM}:${KEY}" | md5sum | sed 's/ .*//')
echo "${USER}:${REALM}:${SIGN}" > ${DIR}/htdigest.passwd
