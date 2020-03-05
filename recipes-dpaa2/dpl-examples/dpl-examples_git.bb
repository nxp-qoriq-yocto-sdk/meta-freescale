DESCRIPTION = "Datapath layout files"
SECTION = "dpaa2"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

DEPENDS = "dtc-native"

S = "${WORKDIR}/git"

inherit deploy

SRC_URI = "git://github.com/qoriq-open-source/dpl-examples.git;nobranch=1;protocol=http"
SRCREV = "2f8a6c2c0607b663afd69aa0477c2047f25031ca"

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
