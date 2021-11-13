# Simple initramfs image artifact generation for tiny images.
DESCRIPTION = "Tiny image capable of booting a device. The kernel includes \
the Minimal RAM-based Initial Root Filesystem (initramfs), which finds the \
first 'init' program more efficiently. core-image-tiny-initramfs doesn't \
actually generate an image but rather generates boot and rootfs artifacts \
that can subsequently be picked up by external image generation tools such as wic."

LICENSE = "MIT"
export IMAGE_BASENAME = "sla-initramfs"

inherit image

PACKAGE_INSTALL = "initramfs-ab-slots busybox e2fsprogs-e2fsck e2fsprogs-mke2fs"
ROOTFS_BOOTSTRAP_INSTALL = ""
KERNELDEPMODDEPEND = ""
ROOTFS_RO_UNNEEDED:append = " ${VIRTUAL-RUNTIME_update-alternatives}"
NO_RECOMMENDED = "0"

# Do not pollute the initrd image with rootfs features
IMAGE_FEATURES = "read-only-rootfs"
IMAGE_LINGUAS = ""

# don't actually generate an image, just the artifacts needed for one
IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"

IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

do_build[depends] = ""
