inherit kernel kernel-arch qoriq_build_64bit_kernel siteinfo

inherit fsl-kernel-localversion
require recipes-kernel/linux/linux-dtb.inc

DESCRIPTION = "Linux kernel for Freescale platforms"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git;branch=sdk-v2.0.x"
SRCREV = "bd51baffc04ecc73f933aee1c3a37c8b44b889a7"

S = "${WORKDIR}/git"

DEPENDS_append = " libgcc dtc-native"
# not put uImage into /boot of rootfs, install kernel-image if needed
RDEPENDS_kernel-base = ""

KERNEL_CC_append = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD_append = " ${TOOLCHAIN_OPTIONS}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"
ZIMAGE_BASE_NAME = "zImage-${PKGE}-${PKGV}-${PKGR}-${MACHINE}-${DATETIME}"

SCMVERSION ?= "y"
LOCALVERSION = ""
DELTA_KERNEL_DEFCONFIG ?= ""
DELTA_KERNEL_DEFCONFIG_prepend_fsl-lsch2 = "freescale.config "
DELTA_KERNEL_DEFCONFIG_prepend_fsl-lsch3 = "freescale.config "
do_configure_prepend() {
    # copy desired defconfig so we pick it up for the real kernel_do_configure
    cp ${KERNEL_DEFCONFIG} .config
    # check if bigendian is enabled
    if [ "${SITEINFO_ENDIANNESS}" = "be" ]; then
         echo "CONFIG_CPU_BIG_ENDIAN=y" >> ${B}/.config
         echo "CONFIG_MTD_CFI_BE_BYTE_SWAP=y" >> ${B}/.config
    fi
    # add config fragments
    for deltacfg in ${DELTA_KERNEL_DEFCONFIG}; do
        if [ -f "${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config ${deltacfg}
        elif [ -f "${WORKDIR}/${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config ${WORKDIR}/${deltacfg}
        elif [ -f "${S}/arch/${ARCH}/configs/${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config \
                ${S}/arch/${ARCH}/configs/${deltacfg}
        fi
    done
    cp .config ${WORKDIR}/defconfig
}

# Fix the dtc compile issue if DTC related options are not enabled in defconfig
do_compile_prepend() {
    mkdir -p ${B}/scripts/dtc
    if [ ! -e ${B}/scripts/dtc/dtc ]; then
        ln -sf ${STAGING_BINDIR_NATIVE}/dtc ${B}/scripts/dtc/dtc
    fi
}

do_install_append_qoriq-arm() {
    install -m 0644 arch/${ARCH}/boot/zImage ${D}/boot/zImage-${KERNEL_VERSION}
    ln -sf  zImage-${KERNEL_VERSION} ${D}/boot/zImage
}

do_deploy_append_qoriq-arm() {
    install -m 0644 arch/${ARCH}/boot/zImage ${DEPLOYDIR}/${ZIMAGE_BASE_NAME}.bin
    ln -sf ${ZIMAGE_BASE_NAME}.bin ${DEPLOYDIR}/zImage-${MACHINE}.bin
    ln -sf ${ZIMAGE_BASE_NAME}.bin ${DEPLOYDIR}/zImage
}

FILES_kernel-image += "/boot/zImage*"

# make everything compatible for the time being
COMPATIBLE_MACHINE = "(qoriq)"
