How to build image
==================

```bash
# in hurry, skip fetching of history by adding `--depth 1 --shallow-submodules`
git clone --recurse-submodules --jobs 4 \
	ssh://git@gitlab.webdev.prusa3d.com:22443/hw/a64/meta-prusa.git \
	oe
cd oe
source oe-init-build-env

bitbake sla-image
```
For convenience a Docker image configuration used for CI builds is included. The build can be executed in a Docker container or the Dockerfile can be used just as a list of dependencies.

### Customers who bought this item also bought ...

#### Other targets

- sla-image-dev: development-enabled μSD image
- sla-bootstrap: μSD image for eMMC bring-up
- sla-update-bundle: OTA & offline update package

#### SDK (cross-toolchain)

A matching [(e)SDK](https://www.yoctoproject.org/docs/latest/sdk-manual/sdk-manual.html) installer can be produced 
alongside an image by adding {+ -c populate_sdk +} or {+ -c populate_sdk_ext +} to the `bitbake [target]` command.
