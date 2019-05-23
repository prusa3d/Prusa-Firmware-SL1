SOC_FAMILY = "sun50i"
include conf/machine/include/soc-family.inc
require conf/machine/include/arm/arch-armv8a.inc

PREFERRED_PROVIDER_virtual/kernel ?= "linux-mainline"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"
KERNEL_IMAGETYPE ?= "Image"

UBOOT_BINARY ?= "u-boot.itb"
SPL_BINARY ?= "sunxi-spl.bin"

PREFERRED_VERSION_u-boot = "v2019.04%"
PREFERRED_VERSION_linux-mainline = "v4.18%"

PREFERRED_PROVIDER_virtual/libgles1 = "mali-blobs"
PREFERRED_PROVIDER_virtual/libgles2 = "mali-blobs"
PREFERRED_PROVIDER_virtual/egl = "mali-blobs"
PREFERRED_PROVIDER_virtual/mesa = "mali-blobs"
PREFERRED_PROVIDER_virtual/libgbm = "mali-blobs"

KERNEL_MODULE_AUTOLOAD_append = " mali "

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "kernel-devicetree"
MACHINE_EXTRA_RDEPENDS += "     \
        kernel-module-mali      \
        mali-blobs              \
"

INITRAMFS_IMAGE = "sla-initramfs"
INITRAMFS_IMAGE_BUNDLE = "1"

IMAGE_CLASSES += "prusa-image"
IMAGE_FSTYPES += "etc factory root"
