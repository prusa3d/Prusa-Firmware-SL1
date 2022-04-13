inherit setuptools3

SUMMARY = "Python library provides gpiod functions"
DESCRIPTION = "Pure python3 library with almost the same usage as libgpiodcxx"
HOMEPAGE = "https://github.com/hhk7734/python3-gpiod"
AUTHOR = "Hyeonki Hong <hhk7734@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=fc4e57275d8c245894e576844466f725"

SRC_URI = "https://files.pythonhosted.org/packages/c9/71/3c0d6a3744a80624324f9572b2b224fc7225fd050cef5df4440b238bcd1e/gpiod-1.5.3.tar.gz"
SRC_URI[md5sum] = "d5467a7bd91834a42e546fa036ff0d87"
SRC_URI[sha256sum] = "35c76009800a715ede673a8ec2b60d426850cc158dfb7c34d937caf197b470db"

S = "${WORKDIR}/gpiod-1.5.3"

RDEPENDS_${PN} = ""
