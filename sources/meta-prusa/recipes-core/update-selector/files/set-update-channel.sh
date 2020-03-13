#!/bin/sh

case $1 in
	stable)
		CHANNEL="stable"
		KEYRING="prod"
		;;
	beta)
		CHANNEL="beta"
		KEYRING="prod"
		;;
	dev)
		CHANNEL="dev"
		KEYRING="dev"
		;;
	*)
		CHANNEL="stable"
		KEYRING="prod"
		;;
esac

echo "Setting: channel: ${CHANNEL}, keyring: ${KEYRING}"

# Set channel
mkdir -p /etc/systemd/system/updater.service.d/
echo -e "[Service]\nEnvironment=TRACK=${CHANNEL}" > /etc/systemd/system/updater.service.d/channel.conf

# Set keyring
mkdir -p /etc/rauc/
cp /usr/share/rauc/ca-${KEYRING}.cert.pem /etc/rauc/ca.cert.pem

# Mark choice
echo ${CHANNEL} > /etc/update_channel
