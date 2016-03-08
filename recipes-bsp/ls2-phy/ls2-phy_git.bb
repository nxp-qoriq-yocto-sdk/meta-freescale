DESCRIPTION = "Firmwares and Standalone Applications"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://Freescale-EULA;md5=f2f28705c8b140b3305c839c20cac6ef"

inherit deploy

SRC_URI = "git://sw-stash.freescale.net/scm/dnnpi/ls2-phy.git;branch=master;protocol=http"
SRCREV = "761f0a0726ce76f2a973d608f4e87265580d8b9c"

S = "${WORKDIR}/git"

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
