# This file is part of the SL1 firmware
# Copyright (C) 2020 Prusa Research a.s. - www.prusa3d.com
# SPDX-License-Identifier: GPL-3.0-or-later

SUMMARY = "Python printer library for Prusa Connect"
HOMEPAGE = "https://github.com/prusa3d/Prusa-Connect-SDK-Printer"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "git://github.com/prusa3d/Prusa-Connect-SDK-Printer.git;protocol=https;branch=master"

SRCREV = "31fc34756204e14371a1d1b2edfd011a8ae046e3"

S = "${WORKDIR}/git"

inherit setuptools3

RDEPENDS:${PN} += "python3-core python3-io python3-requests"
