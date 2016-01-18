DESCRIPTION = "Unified Extensible Firmware Interface for LS1043A"
SECTION = "bootloaders"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

DEPENDS += "util-linux-native u-boot-mkimage-native"

inherit deploy

SRC_URI = "git://sw-stash.freescale.net/scm/dnnpi/ls1043a-uefi.git;branch=edk2-master;protocol=http"
SRCREV = "fde4f64a514363c9134d6f664c6c2a5ed61954be"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git"

do_generate_buildtool() {
    sed -i -e 's/CC = gcc/CC ?= gcc/' \
        -e 's/CXX = g++/CXX ?= g++/' \
        -e 's/AS = gcc/AS ?= gcc/' \
        -e 's/AR = ar/AR ?= ar/' \
        -e 's/LD = ld/LD ?= ld/' \
        -e 's/LINKER ?= $(CC)/LINKER ?= $(LD)/' \
        -e 's/-Werror//' \
        ${S}/BaseTools/Source/C/Makefiles/header.makefile

    make CC="${BUILD_CC} ${BUILD_CFLAGS}" CXX="${BUILD_CXX} ${BUILD_CFLAGS}" \
         LD="${BUILD_CC} ${BUILD_LDFLAGS}" AS="${BUILD_AS}" \
        -C ${S}/BaseTools/Source/C
}
addtask generate_buildtool before do_compile after do_configure

do_compile () {
    export GCC48_AARCH64_PREFIX=${TARGET_PREFIX}
    export EDK_TOOLS_PATH="${S}/BaseTools"
    source ${S}/edksetup.sh BaseTools

    ARCH=${UEFI_BUILD_ARCH}
    TARGET_TOOLS=${UEFI_BUILD_TARGET_TOOLS}
    for board in ${UEFI_MACHINES}; do
        if echo $board |egrep -qi ${MACHINE};then
            build -p ${S}/${UEFI_PATH}Pkg/${board}.dsc -a $ARCH \
                -t $TARGET_TOOLS -b RELEASE
        else
            build -p ${S}/${UEFI_PATH}Pkg/${UEFI_PATH}PkgNonXipBoot.dsc -a $ARCH \
                -t $TARGET_TOOLS -b RELEASE  -D${board}_BOOT_ENABLE=TRUE
        fi

        if echo $board |egrep -qi ${MACHINE};then
            cat ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${UEFI_SOURCE} >> ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${UEFIPI_SOURCE}
            mv  ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${UEFIPI_SOURCE} ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${board}_EFI.fd
        else 
            mv  ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${UEFI_SOURCE} ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${board}_EFI.fd
        fi 

        if echo $board |egrep -q "(NAND|SD)";then                                             
            uboot-mkimage -n ${S}/${UEFI_PATH}Pkg/Library/${UEFI_PBL_PATH}/${MACHINE}_rcw_$(echo $board | tr '[A-Z]' '[a-z]').cfg -R ${S}/${UEFI_PATH}Pkg/Library/${UEFI_PBL_PATH}/${MACHINE}_pbi.cfg -T pblimage -A arm -a 0x10000000 -d ${S}/Build/${UEFI_PATH}/RELEASE_GCC48/FV/${board}_EFI.fd  ${S}/${UEFI_PATH}Pkg/Library/${UEFI_PBL_PATH}/$(echo ${MACHINE} | tr '[a-z]' '[A-Z]')PI_${board}_EFI.pbl
        fi
    done
}

do_install(){
    TARGET_TOOLS=${UEFI_BUILD_TARGET_TOOLS}
    mkdir -p ${D}/boot/
    for board in ${UEFI_MACHINES}; do
        if echo $board |egrep -q "(NAND|SD)";then   
            install ${S}/${UEFI_PATH}Pkg/Library/${UEFI_PBL_PATH}/$(echo ${MACHINE} | tr '[a-z]' '[A-Z]')PI_${board}_EFI.pbl  ${D}/boot/
        fi
        install ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${board}_EFI.fd ${D}/boot/
    done
}

do_deploy(){
    TARGET_TOOLS=${UEFI_BUILD_TARGET_TOOLS}
    mkdir -p ${DEPLOYDIR}
    for board in ${UEFI_MACHINES}; do
        if echo $board |egrep -q "(NAND|SD)";then 
            install ${S}/${UEFI_PATH}Pkg/Library/${UEFI_PBL_PATH}/$(echo ${MACHINE} | tr '[a-z]' '[A-Z]')PI_${board}_EFI.pbl  ${DEPLOYDIR}
        fi
        install ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${board}_EFI.fd ${DEPLOYDIR}
    done
}
addtask deploy after do_install

COMPATIBLE_MACHINE = "(ls1043ardb)"
