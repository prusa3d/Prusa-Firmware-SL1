#!/usr/bin/env python3


"""
# Subproject update script

This script is intended for automatic update of frequently changed subprojects.
It is supposed to lookup most recent commit in target branch of a subproject,
update the hash pointer in the matching Yocto bb file and commit the result
with appropriate commit message.

Together with CI script this should be able to produce builds with all
subprojects bumped to their latest versions on daily basis. Apart from intended
CI usage this also works for pre-release bumps o the subprojects where all the
projects are bumped manually just before the release.

In an ideal world every release is given a name (master, 1.4, 1.6, 2.0, ...)
and all the subprojects (as well as the main repository) contain a branch with
given name so that it is clear what versions should be included. Unfortunately,
this is not the case of this project. To overcome this limitation a dedicated
mapping file is provided to define the branch names. Default mapping is that
subproject branch name is the same as general branch name given to this script.
If necessary this can be overridden with a record in the mapping file. The file
holds a dictionary of project names where each value holds a dictionary of
general branch name - subproject branch name pairs.

Example usage:

# Update and commit all projects, target general branch name is "master"
./update_project.py

# Update and commit all projects, target general branch name is "1.6"
# The mapping forces most of the subprojects to use "master" branch.
./update_project.py 1.6
"""

import json
import re
import sys
from pathlib import Path
from subprocess import check_output, check_call
from tempfile import TemporaryDirectory

MAPPING_FILE = Path("branch_mapping.json")


class Project:
    def __init__(self, git: str, bb: Path):
        self.git = git
        self.bb = bb
        self.name = re.sub(r"(_git)?\.bb", "", bb.name)

    def get_branch(self, meta_branch: str) -> str:
        # Proper mapping is supposed to exist in env, otherwise it is ok to fail
        with MAPPING_FILE.open("r") as fp:
            mapping = json.load(fp)

        if self.name in mapping and meta_branch in mapping[self.name]:
            return mapping[self.name][meta_branch]

        return meta_branch

    def update(self, meta_branch: str):
        project_branch = self.get_branch(meta_branch)

        print(f"Updating project {self.name} reference for {self.git} in {self.bb} branch {project_branch}")

        # Get hashes
        print("Getting hashes")
        remote_ls = check_output(["git", "ls-remote", self.git, f"refs/heads/{project_branch}"], text=True)
        new_hash = re.search(r"^([0-9a-f]*)", remote_ls).group().strip()
        print(f"New hash: {new_hash}.")
        old_hash = re.search(r"SRCREV(:pn-\${PN})? = \"([0-9a-f]*)\"", self.bb.read_text()).groups()[1]
        print(f"Old hash: {old_hash}.")

        if not old_hash:
            raise Exception("Old hash not detected !!!")

        if not new_hash:
            raise Exception("New hash not detected !!!")

        if old_hash == new_hash:
            print("No change")
            return

        # Get history
        print("Getting history")
        with TemporaryDirectory() as clone:
            check_call(["git", "clone", "--bare", self.git, clone])
            changes = check_output(
                ["git", "-C", clone, "log", f"--pretty=format:{self.name}@%h: %s", f"{old_hash}..{new_hash}"],
                text=True,
            )

        # Update bb file
        print("Updating BB file")
        bb_data = self.bb.read_text()
        bb_data = bb_data.replace(old_hash, new_hash)
        self.bb.write_text(bb_data)

        # Commit changes
        message = f"{self.name}: bump\n\n- " + "\n- ".join(changes.split("\n"))

        check_call(
            ["git", "-c", "user.name='Nightly Bump'", "-c", "user.email='CI'", "commit", "--message", message, self.bb]
        )


if len(sys.argv) == 2:
    branch = sys.argv[1]
else:
    branch = "master"

recipes_firmware = Path("sources/meta-prusa/recipes-firmware")
projects = [
    Project("git@gitlab.com:prusa3d/sl1/a64-fw.git", recipes_firmware / "sl1fw/sl1fw_git.bb"),
    Project("git@gitlab.com:prusa3d/sl1/touch-ui.git", recipes_firmware / "touch-ui/touch-ui_git.bb"),
    Project("git@github.com:prusa3d/Prusa-Error-Codes.git", recipes_firmware / "prusa-errors/prusa-errors_git.bb"),
    Project("git@github.com:prusa3d/Prusa-Link-Web.git", recipes_firmware / "prusa-link/prusa-link.bb"),
    Project("git@gitlab.com:prusa3d/sl1/filemanager.git", recipes_firmware / "sl1fw-fs/sl1fw-fs_git.bb"),
    Project("git@gitlab.com:prusa3d/sl1/remote-api.git", recipes_firmware / "sl1fw-api/sl1fw-api_git.bb"),
]

for p in projects:
    p.update(branch)
