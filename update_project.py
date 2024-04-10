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

The script relies completely on the data (repository URL and branch) stored in recipe.
It bumps commits for all machines defined in given recipe.   

Example usage:

# Update and commit all projects.
./update_project.py

"""

import re
from pathlib import Path
from subprocess import check_output, check_call
from tempfile import TemporaryDirectory


class Project:
    def __init__(self, bb: Path):
        self.bb = bb
        keys = ('machine', 'url', 'branch')
        self.name = re.sub(r"(_git)?\.bb", "", bb.name)
        self.repos = []
        with open(bb, 'r') as f:
            content = f.read()
            matches = re.findall(r"SRC\_URI.*?:?(?P<machine>[a-zA-Z0-9-]+)?\ ?\=.*?\n?.*?git(?:sm)?\:\/\/(?P<url>.*?);.*?;branch\=(?P<branch>[a-zA-Z0-9-_/]+)", content)
            if not matches:
               raise SyntaxError("Recipe SRC_URI format not valid. Correct format is: git://<domain>/<path_to_repo>;protocol=<https|ssh>;branch=<branch_name>")
            for repo in matches:
                self.repos.append(dict(zip(keys, repo)))

    def update(self):
        for repo in self.repos:
            # Normalize url
            matches = re.search(r"([\w.-]+)\/(.*\/)([\w-]+)(\.git)", repo["url"]).groups()
            repo["url"] = "git@" + matches[0] + ":" + matches[1] + matches[2] + matches[3]
            repo["name"] = matches[2]

            srcrev_re = ""
            project_machine_name = self.name
            if len(repo['machine']):
                project_machine_name = self.name + ": " + repo["machine"]
                srcrev_re = r":" + repo["machine"]

            print(f"Updating repository {project_machine_name} reference for {repo['url']} in {self.bb} branch {repo['branch']}")

            # Get hashes
            print("Getting hashes")
            remote_ls = check_output(["git", "ls-remote", repo["url"], f"refs/heads/{repo['branch']}"], text=True)
            new_hash = re.search(r"^([0-9a-f]*)", remote_ls).group().strip()
            print(f"New hash: {new_hash}.")
                
            old_hash = re.search(r"SRCREV(:pn-\${PN}" + srcrev_re + r")? = \"([0-9a-f]*)\"", self.bb.read_text()).groups()[1]
            print(f"Old hash: {old_hash}.")

            if not old_hash:
                raise Exception("Old hash not detected !!!")

            if not new_hash:
                raise Exception("New hash not detected !!!")

            if old_hash == new_hash:
                print("No change")
                continue

            # Get history
            print("Getting history")
            with TemporaryDirectory() as clone:
                check_call(["git", "clone", "--bare", repo["url"], clone])
                changes = check_output(
                    ["git", "-C", clone, "log",
                    f"--pretty=format:{repo['name']}@%h: %<(50,trunc)%s",
                    f"{old_hash}..{new_hash}"],
                    text=True,
                )

            # Update bb file
            print("Updating BB file")
            bb_data = self.bb.read_text()
            bb_data = bb_data.replace(old_hash, new_hash)
            self.bb.write_text(bb_data)

            # Commit changes
            message = f"{project_machine_name}: bump\n\n- " + "\n- ".join(changes.split("\n"))

            check_call(
                ["git", "-c", "user.name='Nightly Bump'", "-c", "user.email='CI'", "commit", "--message", message, self.bb]
            )

recipes_firmware = Path("sources/meta-prusa/recipes-firmware")
projects = [
    Project(recipes_firmware / "slafw/slafw_git.bb"),
    Project(recipes_firmware / "touch-ui/touch-ui_git.bb"),
    Project(recipes_firmware / "prusa-errors/prusa-errors_git.bb"),
    Project(recipes_firmware / "prusa-link-web/prusa-link-web.bb"),
    Project(recipes_firmware / "filemanager/filemanager_git.bb"),
    Project(recipes_firmware / "remote-api-link/remote-api-link_git.bb"),
    Project(recipes_firmware / "prusa-connect/prusa-connect_git.bb"),
    Project(recipes_firmware / "prusa-connect-sdk/prusa-connect-sdk_git.bb"),
    Project(recipes_firmware / "mc-fw/mc-fw_git.bb")
]

for p in projects:
    p.update()
