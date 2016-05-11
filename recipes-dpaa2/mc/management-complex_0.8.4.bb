DESCRIPTION = "DPAA2 Management Complex Firmware"
SECTION = "dpaa2"
LICENSE = "Freescale-Binary-EULA"
LIC_FILES_CHKSUM = "file://Freescale-Binary-EULA;md5=da89ad557c4a497fb005e76c2600cda3"

BASEDEPENDS = ""

S = "${WORKDIR}/git"

inherit deploy

SRC_URI = "git://sw-stash.freescale.net/scm/dpaa2/mc-binary.git;branch=master;protocol=http"
SRCREV = "40e7cc7bfc9b35488394450605d649cb7073051a"

do_install () {
    M=`echo ${MACHINE} | sed -e 's,[b-z-]*$,,'`
    install -d ${D}/boot
    install -m 755 ${S}/${M}/*.itb ${D}/boot
}

do_deploy () {
    M=`echo ${MACHINE} | sed -e 's,[b-z-]*$,,'`
    install -d ${DEPLOYDIR}/mc_app
    install -m 755 ${S}/${M}/*.itb ${DEPLOYDIR}/mc_app
    MC_FW=`ls ${S}/${M}/*.itb | xargs basename`
    ln -sf ${MC_FW} ${DEPLOYDIR}/mc_app/mc.itb
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

INHIBIT_PACKAGE_STRIP = "1"
COMPATIBLE_MACHINE = "(ls2080ardb)"
