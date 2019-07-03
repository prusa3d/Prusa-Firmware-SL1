DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

#PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_${PN} = "\
    tmux htop mc man man-pages dtc strace socat iotop minicom \
    i2c-tools tree jq dash fish \
    bash-completion bash-completion-extra \
    systemd-extra-utils systemd-bash-completion systemd-analyze \
    ethtool avrdude gettext \
"
