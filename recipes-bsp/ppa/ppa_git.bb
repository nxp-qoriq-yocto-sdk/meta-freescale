DESCRIPTION = "PPA code"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

DEPENDS += "u-boot-mkimage-native"

inherit deploy

SRC_URI = "git://sw-stash.freescale.net/scm/dnnpi/ls1043-ppa.git;protocol=http \
    file://ppa.its \ 
"

SRCREV = "9dc7b122e6e9e3f3021bc3de1d05270160829429"

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
