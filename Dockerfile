FROM ubuntu:20.04

ENV DEBIAN_FRONTEND noninteractive

# Install tools required to build the system using bitbake
RUN apt-get update && apt-get install -y git build-essential python3 bash chrpath file gawk texinfo perl coreutils tar patch wget findutils diffutils quilt diffstat locales cpio lftp python3-distutils cmake libssl-dev libseccomp-dev gnutls-bin liblz4-tool zstd

# Configure locale, python/bitbake have problems without valid locale
RUN echo "en_US.UTF-8 UTF-8" > /etc/locale.gen
RUN locale-gen
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

# Make bash default
RUN ln -sf /bin/bash /bin/sh

# Install pkcs11-proxy
RUN git clone https://github.com/SUNET/pkcs11-proxy /tmp/pkcs11-proxy && \
    cd /tmp/pkcs11-proxy && cmake . && make && make install && \
    rm -rf /tmp/pkcs11-proxy
ENV RAUC_PKCS11_MODULE /usr/local/lib/libpkcs11-proxy.so

# Create use that will run the build
RUN useradd --create-home --user-group docker-build-user --uid 1234
USER docker-build-user
WORKDIR /home/docker-build-user/

# Setup ssh to trust git server
RUN mkdir -p .ssh
RUN chmod 700 .ssh
RUN ssh-keyscan gitlab.com >> .ssh/known_hosts

# enables GitLab's Interactive Web Terminal feature
EXPOSE 8093
