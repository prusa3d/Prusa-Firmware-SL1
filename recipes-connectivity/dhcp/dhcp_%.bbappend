FILESEXTRAPATHS_prepend := "${THISDIR}/dhcp:"

DEPENDS_append = " patchelf-native "

do_install_append() {
	# For some reason, the libomapi.so library (which is needed by dhclient)
	# references symbols from bind's libdns.so and but then forgets to claim
	# the dependency inside its elf header.

	# So let's find the major version of libdns ...
	ldns=$(echo $(basename $(readlink ${STAGING_DIR_TARGET}/usr/lib/libdns.so)) | cut -d '.' -f1-3)
	# ... so that we can slap it into the NEEDED section of the libomapi.
	patchelf --add-needed ${ldns} ${D}${libdir}/libomapi.so

	# (The dhcp package is being built by autotools, so let this hack be forgiven.)
}
