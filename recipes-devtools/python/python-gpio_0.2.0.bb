SUMMARY = "Linux sysfs gpio access"
DESCRIPTION = "python gpio module for linux using the sysfs file access (/sys/class/gpio). Mimics similar Raspberry Pi IO libraries"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=d3b8fc62a2a27f26a61f1008705465fa"

SRC_URI = "https://files.pythonhosted.org/packages/62/27/020b5bab023f79100ad79d544239887e939f7c5dea7972f80c7fedc46be3/gpio-0.2.0.tar.gz"
SRC_URI[md5sum] = "17aa16c47db01bead36db786a235ac8f"
SRC_URI[sha256sum] = "9fb86a31c759c9dfdff54de3bb170354cd7ab968b009732d656e8049c5e263dc"

inherit setuptools

S="${WORKDIR}/gpio-0.2.0"
