DESCRIPTION = "Reset Control Words (RCW)"
SECTION = "rcw"
LICENSE = "BSD"

#NEED FIX
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD;md5=3775480a712fc46a69647678acb234cb"

inherit deploy

SRC_URI = "git://sw-stash.freescale.net/scm/dpaa2/ls2-rcw.git;branch=master;protocol=http"
SRCREV = "536c50905845d2de7868f331d83de6e34cc3172a"

S = "${WORKDIR}/git"

TP ?= "RDB"

do_install () {
    M=`echo ${MACHINE} | sed -e 's,[b-z-]*$,,'`
    install -d ${D}/boot/rcw
    cp -r ${S}/${M}/${TP}/* ${D}/boot/rcw
}

do_deploy () {
    M=`echo ${MACHINE} | sed -e 's,[b-z-]*$,,'`
    install -d ${DEPLOYDIR}/rcw
    cp -r ${S}/${M}/${TP}/* ${DEPLOYDIR}/rcw
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

INHIBIT_DEFAULT_DEPS = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(ls2080ardb)"
