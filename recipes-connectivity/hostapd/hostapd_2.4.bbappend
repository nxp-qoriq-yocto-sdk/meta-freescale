FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}-${PV}:"

SRC_URI_append_qoriq = " \
    file://0001-Add-DPAA-MACsec-API.patch \
"
