#
# Copyright (C) 2019 Konsulko Group
# Copyright (C) 2019 Roman Beranek
# Based largely on packagegroup-core-base-utils from oe-core
#

SUMMARY = "Full-featured set of base utils"
DESCRIPTION = "Package group bringing in packages needed to provide much of the base utils type functionality found in busybox"

inherit packagegroup

VIRTUAL-RUNTIME_vim ?= "vim-tiny"

RDEPENDS:${PN} = "\
    bash \
    bzip2 \
    coreutils \
    cpio \
    ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "", "debianutils-run-parts", d)} \
    ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "", "dhcp-client", d)} \
    ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "", "dhcp-server", d)} \
    diffutils \
    ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "", "dpkg-start-stop", d)} \
    e2fsprogs \
    ed \
    file \
    findutils \
    gawk \
    grep \
    gzip \
    ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "", "ifupdown", d)} \
    iputils-ping \
    iproute2 \
    iproute2-ss \
    ${@bb.utils.contains("MACHINE_FEATURES", "keyboard", "kbd", "", d)} \
    kmod \
    less \
    lzip \
    ncurses-tools \
    parted \
    patch \
    procps \
    psmisc \
    sed \
    tar \
    time \
    unzip \
    util-linux \
    ${VIRTUAL-RUNTIME_vim} \
    wget \
    which \
    xz \
    "
