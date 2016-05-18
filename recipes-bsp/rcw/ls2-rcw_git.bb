DESCRIPTION = "Reset Control Words (RCW)"
SECTION = "rcw"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/ls2-rcw.git;branch=sdk-v2.0.x"
SRCREV = "74ec3bcdf33fbbedf417b800e0d486d1e8031115"

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
