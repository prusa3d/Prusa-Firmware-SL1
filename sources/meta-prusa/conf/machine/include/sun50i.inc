DEFAULTTUNE ?= "cortexa53-crypto"
require conf/machine/include/tune-cortexa53.inc

include conf/machine/include/soc-family.inc
SOC_FAMILY = "sun50i"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-mainline"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"
KERNEL_IMAGETYPE ?= "Image"

UBOOT_BINARY ?= "u-boot.itb"
SPL_BINARY ?= "sunxi-spl.bin"

PREFERRED_VERSION_u-boot = "v2020.01%"
PREFERRED_VERSION_linux-mainline = "v5.6%"

USE_LIMA ?= "1"
MACHINEOVERRIDES_append = ":gl-${@ 'lima' if d.getVar('USE_LIMA') == '1' else 'mali'}"

PREFERRED_PROVIDER_virtual/libgles1_gl-mali = "mali-blobs"
PREFERRED_PROVIDER_virtual/libgles2_gl-mali = "mali-blobs"
PREFERRED_PROVIDER_virtual/egl_gl-mali = "mali-blobs"
PREFERRED_PROVIDER_virtual/mesa_gl-mali = "mali-blobs"
PREFERRED_PROVIDER_virtual/libgbm_gl-mali = "mali-blobs"

KERNEL_MODULE_AUTOLOAD_append_gl-mali = " mali"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "kernel-devicetree"
MACHINE_EXTRA_RDEPENDS_append_gl-mali = " kernel-module-mali mali-blobs"
MACHINE_EXTRA_RDEPENDS_append_gl-lima = " kernel-module-lima"


PACKAGECONFIG_append_pn-mesa_class-target = " lima kmsro"
PACKAGECONFIG_remove_pn-mpv_class-target = "opengl"

INITRAMFS_IMAGE = "sla-initramfs"
INITRAMFS_IMAGE_BUNDLE = "1"

IMAGE_CLASSES += "prusa-image"
IMAGE_FSTYPES += "etc factory root"
