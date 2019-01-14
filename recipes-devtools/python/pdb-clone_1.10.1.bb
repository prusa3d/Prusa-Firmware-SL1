SUMMARY = "A clone of pdb, fast and with the remote debugging and attach features."
HOMEPAGE = "https://github.com/corpusops/pdbclone.git"
SECTION = "devel/python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=63105df332e0f35fdff817f19b00648d"

inherit pypi setuptools

PYPI_PACKAGE = "pdb-clone"
PYPI_PACKAGE_EXT = "zip"

SRC_URI[md5sum] = "6dca83451b1c4fa473b3b8ce6dc446c7"
SRC_URI[sha256sum] = "61f99ddcfd3cae308729fb08a30a762dec9c19d50ded4e2f4cfc97918283b83d"

DEPENDS = "python"
