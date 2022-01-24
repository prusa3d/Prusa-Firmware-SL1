How to build an image
==================

What to expect
--------------
- Build a custom Linux distribution from source (e.g. Gentoo install). Check the [available targets](###Targets) for more info.
- The procedure takes several hours to complete.
- The RAM usage will be as high as 4 GiB per core of your CPU.
- The build will take around 32 GiB of your storage.

Requirements
------------
Latest Ubuntu LTS will be considered as the host system in the following steps. For a common user, we recommend building the system directly on the host machine. Install all requirements which are listed in a Dockerfile located at the root of this repository. The Docker image created from the Dockerfile is used for CI build.

Clone the repository
--------------------
    git clone git@github.com:prusa3d/Prusa-Firmware-SL1.git
    git tag -a -m "My custom build" my_custom_build
    git submodule init
    git submodule update

Obtain certificates for image signing
-------------------------------------
This step is only needed if you intend to build [Rauc](https://rauc.io/) update bundle by running `bitbake sla-update-bundle`. You can skip the following section if you are interested only in a bootable image for an SD card built by running `bitbake sla-dev-image`.

    cd keys
    sh gen_certs.sh # Skip this if you already have the keys
    sh deploy_certs.sh
    cd ..

Build development SD image
--------------------------
    source ./oe-init-build-env
    bitbake sla-dev-image # other targets are sla-update-bundle, sla-bootstrap

Write the image to the SD card
------------------------------

- With bmaptool

    bmaptool copy tmp/deploy/images/prusa64-sl1/sla-dev-image-prusa64-sl1.wic /dev/mmcblkXXX

- With dd

    dd if=tmp/deploy/images/prusa64-sl1/sla-dev-image-prusa64-sl1.wic of=/dev/mmcblkXXX bs=1M


Customers who bought this item also bought ...
----------------------------------------------

### Targets

- *sla-dev-image*: development-enabled bootable μSD image. To boot into your system just insert the card into the μSD slot and power up the printer. Package manager *opkg* is already included in this target. Any supported package by Yocto can be then built by `bitbake [package-name]`, copied into the printer and installed with opkg.
- *sla-bootstrap*: μSD image for eMMC bring-up. By powering up the printer with this card inserted, both boot slots are being flashed with the same system. When the status LED starts to blink on PrusA64-SL1 board. The flashing is done and you can power off the printer.
- *sla-update-bundle*: OTA & offline update package. There are two [symetric boot slots](https://rauc.readthedocs.io/en/latest/scenarios.html#symmetric-root-fs-slots) in the printer. Users can switch between those to return to the previous system with all its configurations.

### SDK (cross-toolchain)

A matching [(e)SDK](https://www.yoctoproject.org/docs/latest/sdk-manual/sdk-manual.html) installer can be produced
alongside an image by adding {+ -c populate_sdk +} or {+ -c populate_sdk_ext +} to the `bitbake [target]` command.
