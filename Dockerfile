FROM ubuntu:18.04

# Install tools required to build the system using bitbake
RUN apt-get update && apt-get install -y git build-essential python3 bash chrpath file gawk texinfo perl coreutils tar patch wget findutils diffutils quilt diffstat locales python2.7 cpio lftp 

# Configure locale, python/bitbake have problems without valid locale
RUN echo "en_US.UTF-8 UTF-8" > /etc/locale.gen 
RUN locale-gen
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

# Make bash default
RUN ln -sf /bin/bash /bin/sh
# Provide python2 command
RUN ln -sf /usr/bin/python2.7 /usr/bin/python2

# Create use that will run the build
RUN useradd --create-home --user-group appuser
USER appuser
WORKDIR /home/appuser/

# Setup ssh to trust git server
RUN mkdir -p .ssh
RUN chmod 700 .ssh
RUN ssh-keyscan gitlab.com >> .ssh/known_hosts
RUN ssh-keyscan -p 22443 gitlab.webdev.prusa3d.com >> .ssh/known_hosts
RUN ssh-keyscan 10.24.10.12 >> .ssh/known_hosts
