FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

PV = "2020.01"
SRCREV = "303f8fed261020c1cb7da32dad63b610bf6873dd"

SRC_URI = "git://git.denx.de/u-boot.git \
	   file://remove-redundant-yyloc-global.patch \
	   file://CVE-2020-8432.patch \
	   file://CVE-2020-10648-1.patch \
	   file://CVE-2020-10648-2.patch \
          "

LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"
