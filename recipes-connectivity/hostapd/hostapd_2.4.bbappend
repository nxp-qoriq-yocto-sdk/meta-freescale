FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}-${PV}:"

SRC_URI_append_qoriq = " \
    file://0001-Add-DPAA-MACsec-API.patch;patchdir=.. \
"

do_configure_prepend_qoriq() {
    cp ${S}/dpaa_macsec_defconfig ${WORKDIR}/defconfig
}

