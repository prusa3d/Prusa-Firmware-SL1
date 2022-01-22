FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI:append = " \
	   file://0001-kiosk-bg-shell.patch \
	   file://0002-fix-dependencies.patch \
	   file://0003-Revert-kiosk-shell-Add-a-border-fill-to-non-fullscre.patch \
	   file://prusa_logo.webp \
"
SRC_URI:remove = " \
	   file://0001-tests-include-fcntl.h-for-open-O_RDWR-O_CLOEXEC-and-.patch \
	   file://0001-meson.build-fix-incorrect-header.patch \
	   file://0001-libweston-backend-drm-Re-order-gbm-destruction-at-DR.patch \
"
LIC_FILES_CHKSUM:remove = " \
	   file://libweston/compositor.c;endline=27;md5=6c53bbbd99273f4f7c4affa855c33c0a \
"
LIC_FILES_CHKSUM:append = " \
	   file://libweston/compositor.c;endline=27;md5=eb6d5297798cabe2ddc65e2af519bcf0 \
"

PV = "9.0.91"
WESTON_MAJOR_VERSION = "10"
SRC_URI[sha256sum] = "6bc9dbd33b445c1c091e550e8ff2a2dec74e1dc30d8c844f0908d4581bb92f19"

PACKAGECONFIG ?= "kms egl pam systemd webp"

EXTRA_OEMESON = "-Dbackend-default=drm -Dpipewire=false -Dshell-kiosk=true"

copy_prusa_logo() {
	cp ${WORKDIR}/prusa_logo.webp ${S}/data/
}
do_unpack[postfuncs] = "copy_prusa_logo"
