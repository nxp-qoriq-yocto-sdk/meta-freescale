SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=3775480a712fc46a69647678acb234cb"

DEPENDS += "change-file-endianess-native"

inherit deploy siteinfo

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-yocto-sdk/rcw.git;nobranch=1;protocol=http"
SRCREV = "6ae9086528019ab55968da05c25bd319737c8e62"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "BOARDS=${@d.getVar('MACHINE', True).replace('-64b','').replace('-${SITEINFO_ENDIANNESS}','')} DESTDIR=${D}/boot/rcw/"

do_install () {
    oe_runmake install
}
do_install_append_ls102xa () {
    for f in `find ${D}/boot/rcw/ -name "*qspiboot*"`;do
        f_swap=`echo $f |sed -e 's/qspiboot/qspiboot_swap/'`
        tclsh ${STAGING_BINDIR_NATIVE}/byte_swap.tcl $f $f_swap 8
    done
}

do_install_append_fsl-lsch2 () {
     for f in `find ${D}/boot/rcw/ -name "*qspiboot*"`;do
         f_swap=`echo $f |sed -e 's/qspiboot/qspiboot_swap/'`
         tclsh ${STAGING_BINDIR_NATIVE}/byte_swap.tcl $f $f_swap 8
     done 
}

do_deploy () {
    install -d ${DEPLOYDIR}/rcw
    cp -r ${D}/boot/rcw/* ${DEPLOYDIR}/rcw/
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(qoriq)"
