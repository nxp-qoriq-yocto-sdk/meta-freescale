FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_qoriq = " file://0001-ptpd-add-DPAA-and-eTSEC-support.patch"
