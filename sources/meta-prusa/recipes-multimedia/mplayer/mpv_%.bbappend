REQUIRED_DISTRO_FEATURES_remove = "x11"
DEPENDS_remove = "xsp libxv libxscrnsaver libxinerama virtual/libx11"
PACKAGECONFIG = " drm "
EXTRA_OECONF_append = " --disable-x11"
