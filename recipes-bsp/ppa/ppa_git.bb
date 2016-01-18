DESCRIPTION = "PPA code"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://Makefile;beginline=6;endline=17;md5=c614fadbf08e54f2441db4f8236e707b"

DEPENDS += "u-boot-mkimage-native"

inherit deploy

SRC_URI = "git://sw-stash.freescale.net/scm/dnnpi/ls1043-ppa.git;protocol=http \
    file://ppa.its \ 
"

SRCREV = "c53ee117e52afbe087f0c67589e214e9755a3bad"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CC64="${CC}" LD64="${LD}"  OBJ64="${OBJCOPY}""

do_compile() {
    if [ ! -e ${S}/ppa.its ]; then
        cp ${WORKDIR}/ppa.its ${S}
    fi
    export ARMV8_TOOLS_DIR="${STAGING_BINDIR_TOOLCHAIN}"
    export ARMV8_TOOLS_PREFIX="${TARGET_PREFIX}"
    export FILE_NAMES_DIR="${S}/obj"
    oe_runmake all
    uboot-mkimage  -f ppa.its ppa.itb
}

do_install() {
    mkdir -p ${D}/boot/
    install ${S}/ppa.itb  ${D}/boot/
}

do_deploy(){
    mkdir -p ${DEPLOYDIR} 
    install ${S}/ppa.itb  ${DEPLOYDIR}
}

addtask deploy after do_install
PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"   
COMPATIBLE_MACHINE = "(ls1043ardb)"
