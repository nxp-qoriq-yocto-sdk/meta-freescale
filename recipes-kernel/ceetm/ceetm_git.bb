DESCRIPTION = "CEETM TC QDISC"
LICENSE = "GPLv2 & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5881ecf398da8a03a3f4c501e29d287"

SRC_URI = "git://git.freescale.com/ppc/sdk/ceetm.git;branch=sdk-v1.9.x"
SRCREV = "2c79d0b3465368a19bb2b4ccd680ddd297ebe377"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} SYSROOT=${STAGING_DIR_TARGET}"

do_install(){
    mkdir -p ${D}/${libdir}/tc
    cp ${S}/bin/q_ceetm.so ${D}/${libdir}/tc/
}

FILES_${PN} += "${libdir}/tc"
INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(e6500-64b|t1040|t1042|t1023|t1024|t2080|b4860|b4420)"
