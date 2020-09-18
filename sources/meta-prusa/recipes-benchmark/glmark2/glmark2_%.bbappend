FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append = " \
	file://0001-egl-Ensure-eglReleaseThread-is-valid-before-calling-.patch \
"
SRC_URI_append_gl-mali = " \
	file://0002-mali-blobs-workaround-hardcode-EGL-version-as-1.4.patch \
"
PACKAGECONFIG = "drm-gles2 wayland-gles2"

SRCREV_gl-mali = "c17fd14505f30d9e4dbad276f7aa956fd21a637b"
PV_gl-mali = "20190205+${SRCPV}"
