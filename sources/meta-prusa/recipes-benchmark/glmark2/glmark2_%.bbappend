FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append = " \
	file://0001-egl-Ensure-eglReleaseThread-is-valid-before-calling-.patch \
"
SRC_URI_append_gl-mali = " \
	file://0002-mali-blobs-workaround-hardcode-EGL-version-as-1.4.patch \
"
PACKAGECONFIG = "drm-gles2 wayland-gles2"
