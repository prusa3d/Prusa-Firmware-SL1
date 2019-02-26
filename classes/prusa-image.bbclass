ETC_PART_SIZE_MB ?= "16"
ROOTFS_PART_SIZE_MB ?= "768"

python do_prepare_mountpoints() {
    import logging
    import os
    import shutil
    import sys

    from oe.path import copyhardlinktree

    cr_workdir = d.getVar('WORKDIR')
    rootfs_dir = d.getVar('IMAGE_ROOTFS')
    new_rootfs = os.path.realpath(os.path.join(cr_workdir, "root"))
    
    if os.path.lexists(new_rootfs):
        shutil.rmtree(os.path.join(new_rootfs))

    copyhardlinktree(rootfs_dir, new_rootfs)
    mountpoints = ['etc', 'var']
    for m in mountpoints:
        full_path = os.path.realpath(os.path.join(new_rootfs, m))
        for entry in os.listdir(full_path):
            full_entry = os.path.join(full_path, entry)
            if os.path.isdir(full_entry) and not os.path.islink(full_entry):
                shutil.rmtree(full_entry)
            else:
                os.remove(full_entry)
}

IMAGE_CMD_root() {
	dd if=/dev/zero of="${WORKDIR}/root.ext4" count=0 bs=1M seek=${ROOTFS_PART_SIZE_MB}
	mkfs.ext4 -F "${WORKDIR}/root.ext4" -d "${WORKDIR}/root" -L root
	install -m 0644 "${WORKDIR}/root.ext4" "${IMGDEPLOYDIR}/${IMAGE_NAME}.root.ext4"
	ln -sfn "${IMAGE_NAME}.root.ext4" "${IMGDEPLOYDIR}/${IMAGE_BASENAME}-${MACHINE}.root.ext4"
}

IMAGE_CMD_etc() {
        dd if=/dev/zero of="${WORKDIR}/etc.ext4" count=0 bs=1M seek=${ETC_PART_SIZE_MB}
        mkfs.ext4 -F "${WORKDIR}/etc.ext4" -d "${IMAGE_ROOTFS}/etc" -L etc
        install -m 0644 "${WORKDIR}/etc.ext4" "${IMGDEPLOYDIR}/${IMAGE_NAME}.etc.ext4"
	ln -sfn "${IMAGE_NAME}.etc.ext4" "${IMGDEPLOYDIR}/${IMAGE_BASENAME}-${MACHINE}.etc.ext4"
}


addtask do_prepare_mountpoints after do_image before do_image_root

do_image_root[depends] += "e2fsprogs-native:do_populate_sysroot"
do_image_etc[depends] += "e2fsprogs-native:do_populate_sysroot"

do_image_root[respect_exclude_path] = "0"
do_image_etc[respect_exclude_path] = "0"
