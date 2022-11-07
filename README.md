# Prusa-Firmware-SL1

Build enviroment based on the [Yocto project](https://www.yoctoproject.org/) for building GNU\Linux OS used in *Original Prusa SL1* printers.

## Requirements

- Generic GNU\Linux with git and build tools. See [Yocto Project: Quick Start](https://www.yoctoproject.org/docs/1.8/yocto-project-qs/yocto-project-qs.html#yp-resources).
- **Min. 4 GiB** of **RAM** per used core of your CPU.
- **Min. 32 GiB** of your **storage** will be required for finished image and temporary files.
- The procedure **takes several hours** to complete.

## Building the Image

There is optional [Docker](https://www.docker.com/) image where you can find what you need to prepare your customized build enviroment.

### Obtaining the Repository

```console
~$ git clone --recurse-submodules https://github.com/prusa3d/Prusa-Firmware-SL1.git
~$ cd Prusa-Firmware-SL1/
```

### Tagging the Build

It is strongly recommended to tag the repo in order to distinguish it from official builds. The tag defines version string displayed on the printer screen.

```console
Prusa-Firmware-SL1$ git tag -a -m "My stuff" my_stuff
```

Note: For example on your printer screen will be displayed **version** as:

```
my_stuff-special_feature.5-12cfba63d
<Tag name>[-<Branch>.<Commits from tag>-<Last commit hash>]
```

Stuff after *Tag name* will be displayed only if you make changes in the repo after tagging it. 


### (Optional) Obtaining a certificates for image signing

This step is only needed if you intend to build [Rauc](https://rauc.io/) update bundle by running `bitbake sla-update-bundle`. You can skip the following section if you are interested only in a bootable image for an SD card built by running `bitbake sla-dev-image`.

```console
Prusa-Firmware-SL1$ sh ./keys/gen_certs.sh     # Skip this if you already have the keys
Prusa-Firmware-SL1$ sh ./keys/deploy_certs.sh
```

### Making an OS image

#### Submodule init

You need to also intialize submodules by 
`git submodule update --init --recursive` or you might get missing symbolic links error.
Execute this command in terminal in your local repo directory.

#### Targets

Possible targets for deployment:

- **sla-dev-image**: development-enabled bootable μSD image. To boot into your system just insert the card into the μSD slot and power up the printer. Package manager *opkg* is already included in this target. Any supported package by Yocto can be then built by `bitbake [package-name]`, copied into the printer and installed with opkg.
- **sla-bootstrap**: μSD image for eMMC bring-up. By powering up the printer with this card inserted, both boot slots are being flashed with the same system. When the status LED starts to blink on PrusA64-SL1 board. The flashing is done and you can power off the printer.
- **sla-update-bundle**: OTA & offline update package. There are two [symetric boot slots](https://rauc.readthedocs.io/en/latest/scenarios.html#symmetric-root-fs-slots) in the printer. Users can switch between those to return to the previous system with all its configurations.

#### Procedure

```console
Prusa-Firmware-SL1$ source ./oe-init-build-env
Prusa-Firmware-SL1$ bitbake sla-dev-image 
```

Change `sla-dev-image` by desired `Target`.

Add `-c populate_sdk` or `-c populate_sdk_ext` to make [SDK](https://www.yoctoproject.org/docs/latest/sdk-manual/sdk-manual.html) installer script.

#### Important files

Image file: `Prusa-Firmware-SL1/build/tmp/deploy/images/prusa64-sl1/sla-dev-image-prusa64-sl1.wic` 

SDK script: `Prusa-Firmware-SL1/build/tmp/deploy/sdk/prusa-glibc-x86_64-sla-dev-image-cortexa53-crypto-toolchain-*-build.sh`

### Making the SD card

- With bmaptool 
```console
Prusa-Firmware-SL1$ bmaptool copy build/tmp/deploy/images/prusa64-sl1/sla-dev-image-prusa64-sl1.wic /dev/mmcblkXXX
```
*Note:* You need `sla-dev-image-prusa64-sl1.wic.bmap` file also or add `--nobmap` parameter.

- With dd
```console
Prusa-Firmware-SL1$ dd if=build/tmp/deploy/images/prusa64-sl1/sla-dev-image-prusa64-sl1.wic of=/dev/mmcblkXXX bs=1M
```
*Note:* If data was not written properly try to use `conv=fdatasync` to wait for disc cache. TO see what going on use `status=progress` to see what was written.