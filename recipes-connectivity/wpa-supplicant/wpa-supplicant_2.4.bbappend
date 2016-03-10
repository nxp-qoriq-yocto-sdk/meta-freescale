FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

RDEPENDS_${PN} += "libcrypto"

SRC_URI_append_qoriq = " file://0001-Add-DPAA-MACsec-API.patch"

do_configure_prepend_qoriq() {
    cp ${S}/wpa_supplicant/dpaa_macsec_defconfig ${WORKDIR}/defconfig
}
