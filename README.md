How to build image
==================

What to expect
--------------
- Build of a custom Linux distribution from source (e.g. Gentoo install).
- The procedure takes several hours to complete.
- The RAM usage will be as high as 4GiB per each core of your CPU.
- The build will take around 32GiB of your storage.

Requirements
------------
The requirements for the build are listed in a Dockerfile located in the root of this repository. Docker image build using the Dockerfiles is used for CI build.

Clone the respoitory
--------------------

	git clone git@github.com:prusa3d/Prusa-Firmware-SL1.git
	git tag -a -m "My custom build" my_custom_build
	git submodule init
	git submodule update

Obtain keys for image signing
-----------------------------
	cd keys
	sh gen_certs.sh # Skip this if you already have the keys
	sh deploy_certs.sh
	cd ..

Build development SD image
--------------------------
	source ./oe-init-build-env
	bitbake sla-image-dev # other targets are sla-update-bundle, sla-bootstrap

Write the image to the SD card
------------------------------

- With bmaptool


    bmaptool copy tmp/deploy/images/prusa64-sl1/sla-image-dev-prusa64-sl1.wic /dev/mmcblkXXX

- With dd

    dd if=tmp/deploy/images/prusa64-sl1/sla-image-dev-prusa64-sl1.wic of=/dev/mmcblkXXX bs=1M


Customers who bought this item also bought ...
----------------------------------------------

### Other targets

- sla-image-dev: development-enabled μSD image
- sla-bootstrap: μSD image for eMMC bring-up
- sla-update-bundle: OTA & offline update package

### SDK (cross-toolchain)

A matching [(e)SDK](https://www.yoctoproject.org/docs/latest/sdk-manual/sdk-manual.html) installer can be produced 
alongside an image by adding {+ -c populate_sdk +} or {+ -c populate_sdk_ext +} to the `bitbake [target]` command.
