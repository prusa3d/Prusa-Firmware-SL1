SUMMARY = "Pythonic API to Linux uinput kernel module."
HOMEPAGE = "http://tjjr.fi/sw/python-uinput/"
LICENSE = "GPLv3 & GPL-3.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=f27defe1e96c2e1ecd4e0c9be8967949"

SRC_URI = "https://files.pythonhosted.org/packages/54/b7/be7d0e8bbbbd440fef31242974d92d4edd21eb95ed96078b18cf207c7ccb/python-uinput-${PV}.tar.gz"
SRC_URI[md5sum] = "9aaeb0bea686146afcf9ba1fce1eb8c4"
SRC_URI[sha1sum] = "fe65b510b197bd233f7d285976470f2f672cd612"
SRC_URI[sha256sum] = "99392b676c77b5795b86b7d75274db33fe754fd1e06fb3d58b167c797dc47f0c"
SRC_URI[sha384sum] = "e14b29a895301e057c325dc21b6c19c21647c9254eb2c90b45f6f0843b0f48da480a5fa49991de074f9938c0c405ac9a"
SRC_URI[sha512sum] = "47cf878c57da8117010caa3ccbdfe8ee96106b2c7164639c779ea16be05eed2ffea0341c14738c7491ff98827f756772944783d560a747b4b011c76e77693fda"

inherit distutils3

RDEPENDS:${PN} += "python3-core python3-ctypes python3-distutils libudev libinput"
DEPENDS+=" systemd "
