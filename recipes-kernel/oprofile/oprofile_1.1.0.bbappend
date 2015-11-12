FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append = " \
    file://Added-a-patch-for-oprofile-to-fix-the-ppc-type-can-t.patch \
"

EXTRA_OECONF = "--with-kernel=${STAGING_DIR_HOST}${prefix}  --without-x ac_cv_prog_XSLTPROC="
