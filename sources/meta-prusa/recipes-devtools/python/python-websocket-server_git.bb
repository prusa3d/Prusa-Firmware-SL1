SUMMARY = "A simple fully working websocket-server in Python with no external dependencies"

LICENSE = "MIT"

DEPENDS = "python"

inherit setuptools

SRC_URI = "git://github.com/Pithikos/python-websocket-server.git"

#SRC_URI[sha256sum] = "a2707e5b0955be530453350638c1ad66c9b4fd19a59eba51c404201d4ed8863d"
SRCREV_pn-${PN} = "ae6ee7f5d400cde43e2cb89b8c5aec812e927082"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d5bcc2038f9cd3338fc2a1631887a8c7"

S = "${WORKDIR}/git"
