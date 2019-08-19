DESCRIPTION = "omaha protocol for go"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=d2794c0df5b907fdace235a619d80314"

SRC_URI = "git://git@${GO_IMPORT};protocol=ssh;branch=master"
SRCREV = "2c909f6e9567e4f998d93a66c6fd6822eb807fc1"
UPSTREAM_CHECK_COMMITS = "1"

GO_IMPORT = "gitlab.com/prusa3d/${BPN}"
#GO_INSTALL = "${GO_IMPORT}"

inherit go
