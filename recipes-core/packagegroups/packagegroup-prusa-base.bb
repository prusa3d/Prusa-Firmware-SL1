DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_${PN} = "\
    cmake stat gdb rsync tar tmux readline grep lrzsz htop less mc man \
    ninja findutils strace lsof dtc sed util-linux openssh-sftp-server \
    nano time tzdata tzdata-europe tzdata-americas tzdata-posix wget \
    iputils-ping iputils-ping6 bash-completion bash-completion-extra \
    systemd-extra-utils systemd-bash-completion dhcp-client fish curl \
    qtquickcontrols-qmlplugins qttools-plugins qt5-creator iotop jq ell \
    ctags cscope dash vim opencv tree zbar binutils cogl-1.0 git bash \
    diffutils elfutils file i2c-tools lsof lz4 m4 meson minicom mktemp \
    packagegroup-core-ssh-openssh patchelf patch procps quilt socat xz \
    zip unzip iproute2 man-pages openssh-scp openssh-ssh openssh-sftp \
    openssh-sshd openssh-keygen ethtool fstl gcc \
    "
