#!/usr/bin/env python3

# This file is part of the SL1 firmware
# Copyright (C) 2020 Prusa Development a.s. - www.prusa3d.com
# SPDX-License-Identifier: GPL-3.0-or-later

import configparser
import os
import sys
from pathlib import Path

import distro
import pydbus
from gi.repository import GLib
from semver import VersionInfo

try:
    try:
        bundle_path = Path(os.environ["RAUC_BUNDLE_MOUNT_POINT"])
    except KeyError:
        bundle_path = Path(os.environ["RAUC_UPDATE_SOURCE"])

    # Admin check
    try:
        printer0 = pydbus.SystemBus().get("cz.prusa3d.sl1.printer0")
        admin = printer0.admin_enabled
    except (GLib.GError, AttributeError) as e:
        admin = False
        print("Admin query via DBus failed. Considering admin disabled.")

    # Downgrade always allowed
    downgrade_enabled = True

    # Obtain bundle version
    manifest = configparser.ConfigParser()
    manifest.read(bundle_path / "manifest.raucm")
    bundle_version = manifest["update"]["version"]

    # Obtain system version
    system_version = distro.version()

    print(f"Checking update: {system_version} -> {bundle_version}, admin: {admin}, downgrade bundle: {downgrade_enabled}")

    try:
        system_semver = VersionInfo.parse(system_version)
    except ValueError:
        print("System version invalid. Using 0.0.0 instead")
        system_semver = VersionInfo(0, 0, 0)

    try:
        bundle_semver = VersionInfo.parse(bundle_version)
    except ValueError:
        print("Bundle version invalid. Using 0.0.0 instead")
        bundle_semver = VersionInfo(0, 0, 0)

    if bundle_semver == system_semver and not admin:
        print("Same version install not allowed !!!")
        exit(2)

    if bundle_semver < system_semver and not admin and not downgrade_enabled:
        print("Downgrades not allowed")
        exit(1)

except Exception as e:
    print(sys.exc_info())
    print("Exception occurred while comparing versions. Allowing update to fix the broken system.")
    exit(0)
    raise e
