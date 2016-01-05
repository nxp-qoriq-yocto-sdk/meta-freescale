FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

BUSYBOX_SPLIT_SUID_qoriq = "0"

SRC_URI_append_qoriq = " file://defconfig-fsl"

do_configure_prepend_qoriq () {
    cp ${WORKDIR}/defconfig-fsl ${WORKDIR}/defconfig
}

