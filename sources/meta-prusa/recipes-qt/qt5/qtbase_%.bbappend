DEPENDS_append = " patchelf-native "

do_abuse_recipe_sysroot() {
	l=libMali.so.6
	cp -p ${STAGING_LIBDIR}/${l} ${STAGING_LIBDIR}/${l}~
	mv -f ${STAGING_LIBDIR}/${l}~ ${STAGING_LIBDIR}/${l}
	patchelf --set-soname ${l} ${STAGING_LIBDIR}/${l}
}

addtask abuse_recipe_sysroot after do_prepare_recipe_sysroot before do_configure
