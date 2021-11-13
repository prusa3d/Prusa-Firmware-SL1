FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI:append = " \
	file://0001-egl-Ensure-eglReleaseThread-is-valid-before-calling-.patch \
"
PACKAGECONFIG = "drm-gles2 wayland-gles2"
