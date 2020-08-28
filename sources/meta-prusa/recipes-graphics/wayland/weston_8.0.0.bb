SUMMARY = "Weston, a Wayland compositor"
DESCRIPTION = "Weston is the reference implementation of a Wayland compositor"
HOMEPAGE = "http://wayland.freedesktop.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=d79ee9e66bb0f95d3386a7acae780b70 \
                    file://libweston/compositor.c;endline=27;md5=6c53bbbd99273f4f7c4affa855c33c0a"

SRC_URI = "https://wayland.freedesktop.org/releases/${BPN}-${PV}.tar.xz \
           file://weston.ini \
           file://weston.service \
           file://weston-framebuffer.service \
           file://0001-weston-launch-Provide-a-default-version-that-doesn-t.patch \
           file://0002-added-framebuffer-simple-client.patch \
	   file://0003-kiosk-shell-Introduce-kiosk-fullscreen-shell-for-des.patch \
	   file://0004-kiosk-bg-shell.patch \
	   file://prusa_logo.webp \
"
SRC_URI[md5sum] = "53e4810d852df0601d01fd986a5b22b3"
SRC_URI[sha256sum] = "7518b49b2eaa1c3091f24671bdcc124fd49fc8f1af51161927afa4329c027848"

UPSTREAM_CHECK_URI = "https://wayland.freedesktop.org/releases.html"

inherit meson pkgconfig distro_features_check systemd

# depends on virtual/egl
REQUIRED_DISTRO_FEATURES = "opengl"

DEPENDS = "libxkbcommon gdk-pixbuf pixman cairo glib-2.0 jpeg"
DEPENDS += "wayland wayland-protocols libinput virtual/egl pango wayland-native"

WESTON_MAJOR_VERSION = "${@'.'.join(d.getVar('PV').split('.')[0:1])}"

EXTRA_OEMESON += "-Dbackend-default=drm -Dbackend-rdp=false -Dpipewire=false -Dremoting=false"
EXTRA_OEMESON_append = " -Dshell-ivi=false -Dshell-fullscreen=false -Dshell-desktop=false -Dshell-kiosk=true"

PACKAGECONFIG ?= "kms egl clients pam systemd webp"

#
# Compositor choices
#
# Weston on KMS
PACKAGECONFIG[kms] = "-Dbackend-drm=true,-Dbackend-drm=false,drm udev virtual/egl virtual/libgles2 virtual/libgbm mtdev"
# Weston on Wayland (nested Weston)
PACKAGECONFIG[wayland] = "-Dbackend-wayland=true,-Dbackend-wayland=false,virtual/egl virtual/libgles2"
# Weston on X11
PACKAGECONFIG[x11] = "-Dbackend-x11=true,-Dbackend-x11=false,virtual/libx11 libxcb libxcb libxcursor cairo"
# Headless Weston
PACKAGECONFIG[headless] = "-Dbackend-headless=true,-Dbackend-headless=false"
# Weston on framebuffer
PACKAGECONFIG[fbdev] = "-Dbackend-fbdev=true,-Dbackend-fbdev=false,udev mtdev"
# weston-launch
PACKAGECONFIG[launch] = "-Dweston-launch=true,-Dweston-launch=false,drm"
# VA-API desktop recorder
PACKAGECONFIG[vaapi] = "-Dbackend-drm-screencast-vaapi=true,-Dbackend-drm-screencast-vaapi=false,libva"
# Weston with EGL support
PACKAGECONFIG[egl] = "-Drenderer-gl=true,-Drenderer-gl=false,virtual/egl"
# Weston with lcms support
PACKAGECONFIG[lcms] = "-Dcolor-management-lcms=true,-Dcolor-management-lcms=false,lcms"
# Weston with webp support
PACKAGECONFIG[webp] = "-Dimage-webp=true,-Dimage-webp=false,libwebp"
# Weston with systemd-login support
PACKAGECONFIG[systemd] = "-Dsystemd=true -Dlauncher-logind=true,-Dsystemd=false -Dlauncher-logind=false,systemd dbus"
# Weston with Xwayland support (requires X11 and Wayland)
PACKAGECONFIG[xwayland] = "-Dxwayland=true,-Dxwayland=false"
# colord CMS support
PACKAGECONFIG[colord] = "-Dcolor-management-colord=true,-Dcolor-management-colord=false,colord"
# Clients support
PACKAGECONFIG[clients] = "-Dsimple-clients=all -Ddemo-clients=true,-Dsimple-clients= -Ddemo-clients=false"
# Virtual remote output with GStreamer on DRM backend
PACKAGECONFIG[remoting] = "-Dremoting=true,-Dremoting=false,gstreamer-1.0"
# Weston with PAM support
PACKAGECONFIG[pam] = "-Dpam=true,-Dpam=false,libpam"

SYSTEMD_PACKAGES = "${PN} ${PN}-framebuffer"
SYSTEMD_SERVICE_${PN} = "weston.service"
SYSTEMD_SERVICE_${PN}-framebuffer = "weston-framebuffer.service"

do_install_append() {
	# Weston doesn't need the .la files to load modules, so wipe them
	rm -f ${D}/${libdir}/libweston-${WESTON_MAJOR_VERSION}/*.la
	install -d ${D}${sysconfdir}/xdg/weston
	install -m 0644 ${WORKDIR}/weston.ini ${D}${sysconfdir}/xdg/weston/
	install -d ${D}${systemd_system_unitdir}/graphical.target.wants
	install -d ${D}${systemd_system_unitdir}/multi-user.target.wants
	install -m 0644 ${WORKDIR}/weston.service ${D}${systemd_system_unitdir}/
	install -m 0644 ${WORKDIR}/weston-framebuffer.service ${D}${systemd_system_unitdir}/
	ln -sf ../weston-framebuffer.service ${D}${systemd_system_unitdir}/graphical.target.wants/
	ln -sf ../graphical.target ${D}${systemd_system_unitdir}/multi-user.target.wants/
}

PACKAGES += "libweston-${WESTON_MAJOR_VERSION} ${PN}-framebuffer ${PN}-examples"

FILES_${PN}-dev += "${libdir}/${BPN}/libexec_weston.so"
FILES_${PN} = "${bindir}/weston ${bindir}/weston-terminal ${bindir}/weston-info ${bindir}/weston-launch ${bindir}/wcap-decode ${libexecdir} ${libdir}/${BPN}/*.so* ${datadir}"

FILES_libweston-${WESTON_MAJOR_VERSION} = "${libdir}/lib*${SOLIBS} ${libdir}/libweston-${WESTON_MAJOR_VERSION}/*.so"
SUMMARY_libweston-${WESTON_MAJOR_VERSION} = "Helper library for implementing 'wayland window managers'."

FILES_${PN}-examples = "${bindir}/*"
FILES_${PN}-framebuffer = " \
	${bindir}/weston-simple-framebuffer \
	${systemd_system_unitdir}/weston-framebuffer.service \
	${systemd_system_unitdir}/graphical.target.wants/weston-framebuffer.service \
"

RDEPENDS_${PN} += "xkeyboard-config"
RRECOMMENDS_${PN} = "liberation-fonts"
RRECOMMENDS_${PN}-dev += "wayland-protocols"


FILES_${PN} += " \
	${sysconfdir}/xdg/weston/weston.ini \
	${systemd_system_unitdir}/weston.service \
	${systemd_system_unitdir}/multi-user.target.wants/graphical.target \
"
CONFFILES_${PN} += "${sysconfdir}/xdg/weston/weston.ini"

copy_prusa_logo() {
	cp ${WORKDIR}/prusa_logo.webp ${S}/data/
}
do_unpack[postfuncs] = "copy_prusa_logo"
