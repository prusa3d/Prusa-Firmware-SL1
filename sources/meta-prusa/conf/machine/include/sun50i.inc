DEFAULTTUNE ?= "cortexa53-crypto"
require conf/machine/include/arm/armv8a/tune-cortexa53.inc

include conf/machine/include/soc-family.inc
SOC_FAMILY = "sun50i"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-mainline"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"
KERNEL_IMAGETYPE ?= "Image"

UBOOT_INITIAL_ENV = "u-boot-initial-env"
UBOOT_BINARY = "u-boot-sunxi-with-spl.bin"

PREFERRED_VERSION_linux-mainline = "v5.11%"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "kernel-devicetree"
MACHINE_EXTRA_RDEPENDS:append = " kernel-module-lima"

PACKAGECONFIG:append:pn-mesa:class-target = " lima kmsro"
PACKAGECONFIG:remove:pn-mesa = "virgl elf-tls"

INITRAMFS_IMAGE = "sla-initramfs"
INITRAMFS_IMAGE_BUNDLE = "1"

IMAGE_CLASSES += "prusa-image"
IMAGE_FSTYPES += "etc factory root"
