ETC_PART_SIZE_MB ?= "16"
ROOTFS_PART_SIZE_MB ?= "768"
FACTORY_PART_SIZE_MB ?= "64"

rootfsdir = "${WORKDIR}/root"
etcdir = "${IMAGE_ROOTFS}/etc"
factorydir = "${IMAGE_ROOTFS}/usr/share/factory/defaults"
vardir = "${IMAGE_ROOTFS}/var"

def check_size_limits(d):
    import os, subprocess

    for p in ['rootfs', 'etc', 'factory']:
        max_size = int(d.getVar(p.upper() + '_PART_SIZE_MB'))
        dir = os.path.join(d.getVar('IMAGE_ROOTFS'), d.getVar(p + 'dir'))
        actual_size = int(subprocess.check_output(['du', '-lms', dir]).split()[0])

        if actual_size > max_size:
            bb.fatal("The %s image size (%d MB) exceeds %s_PART_SIZE_MB of (%d MB)" % \
                (p, actual_size, p.upper(), max_size))


python do_populate_mountpoints() {
    import logging
    import os
    import shutil
    import sys

    from oe.path import copytree

    basedir = d.getVar('IMAGE_ROOTFS')
    rootfsdir = d.getVar('rootfsdir')
    
    if os.path.lexists(rootfsdir):
        shutil.rmtree(rootfsdir)

    copytree(basedir, rootfsdir)
    for m in ['etc', 'usr/share/factory/defaults', 'var']:
        full_path = os.path.join(rootfsdir, m)
        for entry in os.listdir(full_path):
            full_entry = os.path.join(full_path, entry)
            if os.path.isdir(full_entry) and not os.path.islink(full_entry):
                shutil.rmtree(full_entry)
            else:
                os.remove(full_entry)

    check_size_limits(d)
}

IMAGE_CMD_root() {
	dd if=/dev/zero of="${WORKDIR}/root.ext4" count=0 bs=1M seek=${ROOTFS_PART_SIZE_MB}
	mkfs.ext4 -F -i 4096 "${WORKDIR}/root.ext4" -d "${WORKDIR}/root" -L root
	install -m 0644 "${WORKDIR}/root.ext4" "${IMGDEPLOYDIR}/${IMAGE_NAME}.root.ext4"
	ln -sfn "${IMAGE_NAME}.root.ext4" "${IMGDEPLOYDIR}/${IMAGE_BASENAME}-${MACHINE}.root.ext4"
}

IMAGE_CMD_etc() {
        dd if=/dev/zero of="${WORKDIR}/etc.ext4" count=0 bs=1M seek=${ETC_PART_SIZE_MB}
        mkfs.ext4 -F "${WORKDIR}/etc.ext4" -d "${IMAGE_ROOTFS}/etc" -L etc
        install -m 0644 "${WORKDIR}/etc.ext4" "${IMGDEPLOYDIR}/${IMAGE_NAME}.etc.ext4"
	ln -sfn "${IMAGE_NAME}.etc.ext4" "${IMGDEPLOYDIR}/${IMAGE_BASENAME}-${MACHINE}.etc.ext4"
}

IMAGE_CMD_factory() {
	dd if=/dev/zero of="${WORKDIR}/factory.ext4" count=0 bs=1M seek=${FACTORY_PART_SIZE_MB}
	mkfs.ext4 -F "${WORKDIR}/factory.ext4" -d "${IMAGE_ROOTFS}/usr/share/factory/defaults" -L factory
	install -m 0644 "${WORKDIR}/factory.ext4" "${IMGDEPLOYDIR}/${IMAGE_NAME}.factory.ext4"
	ln -sfn "${IMAGE_NAME}.factory.ext4" "${IMGDEPLOYDIR}/${IMAGE_BASENAME}-${MACHINE}.factory.ext4"
}


addtask do_populate_mountpoints after do_rootfs before do_image_root do_image_etc do_image_factory

do_image_root[depends] += "e2fsprogs-native:do_populate_sysroot"
do_image_etc[depends] += "e2fsprogs-native:do_populate_sysroot"
do_image_factory[depends] += "e2fsprogs-native:do_populate_sysroot"

do_image_root[deptask] = "do_populate_mountpoints do_image"
do_image_etc[deptask] = "do_populate_mountpoints do_image"
do_image_factory[deptask] = "do_populate_mountpoints do_image"

do_populate_mountpoints[deptask] = "do_rootfs"

do_image_root[respect_exclude_path] = "0"
do_image_etc[respect_exclude_path] = "0"
do_image_factory[respect_exclude_path] = "0"
