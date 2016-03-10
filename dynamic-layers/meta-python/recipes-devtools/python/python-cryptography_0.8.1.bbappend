FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_qoriq = " file://work-with-openssl-1.0.2.patch"
