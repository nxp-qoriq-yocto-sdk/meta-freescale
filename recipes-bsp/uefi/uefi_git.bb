DESCRIPTION = "Unified Extensible Firmware Interface for LS1043A"
SECTION = "bootloaders"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

DEPENDS += "util-linux-native u-boot-mkimage-native"

inherit deploy

SRC_URI = "git://sw-stash.freescale.net/scm/dnnpi/ls1043a-uefi.git;branch=edk2-master;protocol=http \
           git://sw-stash.freescale.net/scm/dnnpi/ls1043a-uefi-fatpkg.git;branch=master;protocol=http;name=FatPkg;destsuffix=git/FatPkg \
"
SRCREV = "368f7b6a4f3ead10dce4bf5e4dab92d6170ae216"
SRCREV_FatPkg ="a108418f3871c2ca44d00fa0c1d0544362c41536"

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
    export GCC49_AARCH64_PREFIX=${TARGET_PREFIX}
    export EDK_TOOLS_PATH="${S}/BaseTools"
    source ${S}/edksetup.sh BaseTools

    ARCH=${UEFI_BUILD_ARCH}
    TARGET_TOOLS=${UEFI_BUILD_TARGET_TOOLS}
    for board in ${UEFI_MACHINES}; do
        case "${board}" in
            *XIP*)   BootSuffix="XipBoot.dsc";;
            *FATXIP*)   BootSuffix="FatXipBoot.dsc";;
            *NAND*)   BootSuffix="NonXipBoot.dsc";;
            *SD*)   BootSuffix="NonXipBoot.dsc";
        esac
   
        if echo $board |egrep -qi "XIP|FATXIP";then
            build -p ${S}/${UEFI_PATH}Pkg/${UEFI_PATH}Pkg${BootSuffix} -a $ARCH \
                -t $TARGET_TOOLS -b RELEASE
        else
            build -p ${S}/${UEFI_PATH}Pkg/${UEFI_PATH}Pkg${BootSuffix} -a $ARCH \
                -t $TARGET_TOOLS -b RELEASE  -D${board}_BOOT_ENABLE=TRUE
        fi

        if echo $board |egrep -qi "XIP|FATXIP";then
            cat ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${UEFI_SOURCE} >> ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${UEFIPI_SOURCE}
            mv  ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${UEFIPI_SOURCE} ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${board}_EFI.fd
        else 
            mv  ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${UEFI_SOURCE} ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${board}_EFI.fd
        fi 

        if echo $board |egrep -q "(NAND|SD)";then                                             
            uboot-mkimage -n ${S}/${UEFI_PATH}Pkg/Library/${UEFI_PBL_PATH}/${MACHINE}_rcw_$(echo $board | tr '[A-Z]' '[a-z]').cfg -R ${S}/${UEFI_PATH}Pkg/Library/${UEFI_PBL_PATH}/${MACHINE}_pbi.cfg -T pblimage -A arm -a 0x10000000 -d ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/FV/${UEFIPI_SOURCE}  ${S}/${UEFI_PATH}Pkg/Library/${UEFI_PBL_PATH}/$(echo ${MACHINE} | tr '[a-z]' '[A-Z]')PI_${board}_EFI.pbl
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
    if [ -e ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/${UEFI_BUILD_ARCH}/LinuxLoader.efi ]; then
        install ${S}/Build/${UEFI_PATH}/RELEASE_${TARGET_TOOLS}/${UEFI_BUILD_ARCH}/LinuxLoader.efi   ${DEPLOYDIR}
    fi
   
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"
COMPATIBLE_MACHINE = "(ls1043ardb)"
