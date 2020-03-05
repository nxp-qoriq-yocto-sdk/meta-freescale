DESCRIPTION = "Firmwares and Standalone Applications"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

inherit deploy

SRC_URI = "file://ls2-phy-fsl-sdk-v2.0.tar.bz2"
SRC_URI[md5sum] = "788d786e5774d031817cffae704c83f5"
SRC_URI[sha256sum] = "59a7eba0f4cb08eddb16a583523559e134891fdca9ea662cdc13a43f1574e815"

S = "${WORKDIR}/ls2-phy-fsl-sdk-v2.0"

do_install () {
    install -d ${D}/boot
    cp -fr ${S}/AQR405 ${D}/boot
    cp -fr ${S}/CS4340 ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/ls2-phy
    cp -fr ${S}/AQR405 ${DEPLOYDIR}/ls2-phy
    cp -fr ${S}/CS4340 ${DEPLOYDIR}/ls2-phy
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"
COMPATIBLE_MACHINE = "(ls2080ardb)"
