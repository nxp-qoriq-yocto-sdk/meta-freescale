DESCRIPTION = "Reset Control Words (RCW)"
SECTION = "rcw"
LICENSE = "Freescale-Binary-EULA"
LIC_FILES_CHKSUM = "file://Freescale-Binary-EULA;md5=8835a59e50213e1b21243dd00c933e47"

inherit deploy

SRC_URI = "git://github.com/nxp/qoriq-rcw-bin.git;nobranch=1;protocol=http"
SRCREV = "ef79fb9f96cd1bae1a122df54c61a05c91c11372"

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
