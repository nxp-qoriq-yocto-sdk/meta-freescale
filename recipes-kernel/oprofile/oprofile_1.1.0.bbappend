FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append = " \
    file://Added-a-patch-for-oprofile-to-fix-the-ppc-type-can-t.patch \
"
