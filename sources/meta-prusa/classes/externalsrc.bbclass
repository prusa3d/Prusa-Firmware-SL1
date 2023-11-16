inherit externalsrc

def srctree_hash_files(d, srcdir=None):
    import shutil
    import subprocess
    import tempfile
    import hashlib

    s_dir = srcdir or d.getVar('EXTERNALSRC')
    git_dir = None

    try:
        git_dir = os.path.join(s_dir,
            subprocess.check_output(['git', '-C', s_dir, 'rev-parse', '--git-dir'], stderr=subprocess.DEVNULL).decode("utf-8").rstrip())
        top_git_dir = os.path.join(s_dir, subprocess.check_output(['git', '-C', d.getVar("TOPDIR"), 'rev-parse', '--git-dir'],
            stderr=subprocess.DEVNULL).decode("utf-8").rstrip())
        if git_dir == top_git_dir:
            git_dir = None
    except subprocess.CalledProcessError:
        pass

    ret = " "
    if git_dir is not None:
        oe_hash_file = os.path.join(git_dir, 'oe-devtool-tree-sha1-%s' % d.getVar('PN'))
        with tempfile.NamedTemporaryFile(prefix='oe-devtool-index') as tmp_index:
            # Clone index
            shutil.copyfile(os.path.join(git_dir, 'index'), tmp_index.name)
            # Update our custom index
            env = os.environ.copy()
            env['GIT_INDEX_FILE'] = tmp_index.name
            subprocess.check_output(['git', 'add', '-A', '.'], cwd=s_dir, env=env)
            git_sha1 = subprocess.check_output(['git', 'write-tree'], cwd=s_dir, env=env).decode("utf-8")
            if os.path.exists(".gitmodules"):
                submodule_helper = subprocess.check_output(["git", "config", "--file", ".gitmodules", "--get-regexp", "path"], cwd=s_dir, env=env).decode("utf-8")
                for line in submodule_helper.splitlines():
                    module_dir = os.path.join(s_dir, line.rsplit(maxsplit=1)[1])
                    if os.path.isdir(module_dir):
                        proc = subprocess.Popen(['git', 'add', '-A', '.'], cwd=module_dir, env=env, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
                        proc.communicate()
                        proc = subprocess.Popen(['git', 'write-tree'], cwd=module_dir, env=env, stdout=subprocess.PIPE, stderr=subprocess.DEVNULL)
                        stdout, _ = proc.communicate()
                        git_sha1 += stdout.decode("utf-8")
            sha1 = hashlib.sha1(git_sha1.encode("utf-8")).hexdigest()
        with open(oe_hash_file, 'w') as fobj:
            fobj.write(sha1)
        ret = oe_hash_file + ':True'
    else:
        ret = s_dir + '/*:True'
    return ret
