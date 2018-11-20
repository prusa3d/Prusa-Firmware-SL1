DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_${PN} = "\
    tmux lrzsz htop less mc man man-pages sed dtc lsof strace \
    findutils socat xz procps tar rsync util-linux zip unzip \
    iotop htop minicom file i2c-tools tree jq wget dash fish \
    bash-completion bash-completion-extra \
    systemd-extra-utils systemd-bash-completion systemd-analyze \
    ethtool avrdude \
"
