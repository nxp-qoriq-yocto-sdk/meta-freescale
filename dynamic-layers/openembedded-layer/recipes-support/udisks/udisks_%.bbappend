# The original recipe hardcodes "/lib" rather than using the proper base_libdir
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://base_libdir.patch \
"

