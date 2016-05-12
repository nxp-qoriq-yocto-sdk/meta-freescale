DESCRIPTION = "Datapath layout files"
SECTION = "dpaa2"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

DEPENDS = "dtc-native"

S = "${WORKDIR}/git"

inherit deploy

SRC_URI = "git://sw-stash.freescale.net/scm/dpaa2/dpl-examples.git;branch=master;protocol=http"
SRCREV = "e3d836ba298c5136d5819e28e8184a3654a2f9db"

TP ?= "RDB"

do_install () {
    M=`echo ${MACHINE} | sed -e 's,[b-z-]*$,,'`
    install -d ${D}/boot
    install -m 644 ${S}/${M}/${TP}/*.dtb ${D}/boot
}

do_deploy () {
    M=`echo ${MACHINE} | sed -e 's,[b-z-]*$,,'`
    install -d ${DEPLOYDIR}/dpl-examples
    install -m 644 ${S}/${M}/${TP}/*.dtb ${DEPLOYDIR}/dpl-examples
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(ls2080ardb)"
