FROM ubuntu:20.04

ENV DEBIAN_FRONTEND noninteractive

# Install tools required to build the system using bitbake
RUN \
    apt-get update && \
    apt-get install -y \
    --no-install-recommends \
        git \
        ssh \
        build-essential \
        bash \
        chrpath \
        file \
        gawk \
        texinfo \
        perl \
        coreutils \
        tar \
        patch \
        wget \
        findutils \
        diffutils \
        quilt \
        diffstat \
        locales \
        cpio \
        lftp \
        cmake \
        libssl-dev \
        libseccomp-dev \
        gnutls-bin \
        liblz4-tool \
        zstd \
        bmap-tools \
        # Python3 related
        python3 \
        python3-pip \
        python3-distutils \
        #
        && rm -rf /var/lib/apt/lists/*
# 
# Configure locale, python/bitbake have problems without valid locale
RUN \
    echo "en_US.UTF-8 UTF-8" > /etc/locale.gen && \
    locale-gen
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

# Make bash default
RUN ln -sf /bin/bash /bin/sh

# Install pkcs11-proxy
RUN \
    git config --global http.sslVerify "false" && \
    git clone https://github.com/SUNET/pkcs11-proxy /tmp/pkcs11-proxy && \
    cd /tmp/pkcs11-proxy && cmake . && make && make install && \
    rm -rf /tmp/pkcs11-proxy
ENV RAUC_PKCS11_MODULE /usr/local/lib/libpkcs11-proxy.so

# Python3: Install 
RUN \
    # PySerial
    python3 -m pip install --no-cache-dir pyserial && \
    # usbsdmux
    python3 -m pip install --no-cache-dir usbsdmux && \
    # We want to add usbsdmux UDEV rules as well
    mkdir -p /etc/udev/rules.d/ && \
    wget https://raw.githubusercontent.com/linux-automation/usbsdmux/master/contrib/udev/99-usbsdmux.rules -O /etc/udev/rules.d/99-usbsdmux.rules

# Create use that will run the build
RUN \
    useradd \
    --create-home \
    --user-group docker-build-user \
    --uid 1234 && \
    usermod -aG plugdev,dialout,disk docker-build-user
USER docker-build-user
WORKDIR /home/docker-build-user/

# Setup ssh to trust git server
# Consolidate to prevent issues
RUN \
    mkdir -p .ssh && \
    chmod 700 .ssh && \
    ssh-keyscan gitlab.com >> .ssh/known_hosts && \
    git config --global safe.directory /builds/prusa3d/sl1/meta-sl1

# enables GitLab's Interactive Web Terminal feature
EXPOSE 8093
