# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

SUMMARY = "A simple wrapper around inotify. No fancy bells and whistles, just a literal wrapper with ctypes. Under 100 lines of code!"
HOMEPAGE = "https://github.com/chrisjbillington/inotify_simple"
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=28a8c10291bba0bc0f8b9d5e87c0d826"

SRC_URI = "https://files.pythonhosted.org/packages/51/41/59ca6011f5463d5e5eefcfed2e7fe470922d3a958b7f3aad95eda208d7d3/inotify_simple-${PV}.tar.gz"
SRC_URI[md5sum] = "b7ca2ffa816dea85ef5023cde4e72b8b"
SRC_URI[sha1sum] = "a34d8d46f70236412074c5c7442fdae3151753ca"
SRC_URI[sha256sum] = "8440ffe49c4ae81a8df57c1ae1eb4b6bfa7acb830099bfb3e305b383005cc128"
SRC_URI[sha384sum] = "7950d3ea3527cce309ecaa7ccf45b832a1ba9b385fb42af8c91bc88f4293ecec70ead58b0a16cdacb52e19365985a08c"
SRC_URI[sha512sum] = "fa8048786e6d4771ca63d6a2cc3e4230ec68a2d6f4bf8ca5a06056d0d261a678c3769112796a4e6a0f9c6be7331a16c4a06283d0240debb6ab407cd86b460c49"

S = "${WORKDIR}/inotify_simple-${PV}"

inherit setuptools3

# WARNING: the following rdepends are determined through basic analysis of the
# python sources, and might not be 100% accurate.
RDEPENDS:${PN} += "python3-core python3-ctypes python3-fcntl python3-io"
