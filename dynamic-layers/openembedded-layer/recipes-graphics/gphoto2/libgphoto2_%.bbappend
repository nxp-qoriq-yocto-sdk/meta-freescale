# We need to ship both lib and lib64 on powerpc64
FILES_${PN} += "${nonarch_base_libdir}/udev/*"
FILES_${PN}-dbg += "${exec_prefix}/lib/*/*/.debug"
FILES_${PN}-dev += "${exec_prexix}/lib/*/*/*.la"
