DESCRIPTION = "CEETM TC QDISC"
LICENSE = "GPLv2 & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5881ecf398da8a03a3f4c501e29d287"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-yocto-sdk/ceetm.git;nobranch=1;protocol=http"
SRCREV = "a237e094c3316a55727b855d15480e664545316a"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} SYSROOT=${STAGING_DIR_TARGET}"

do_install(){
    mkdir -p ${D}/${libdir}/tc
    cp ${S}/bin/q_ceetm.so ${D}/${libdir}/tc/
}

FILES_${PN} += "${libdir}/tc"
INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(e6500-64b|t1040|t1042|t1023|t1024|t2080|b4860|b4420|ls1043aqds|ls1043ardb)"
