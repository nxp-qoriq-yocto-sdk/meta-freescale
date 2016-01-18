DESCRIPTION = "A FIT image comprising the Linux image, dtb and rootfs image"
LICENSE = "MIT"

SRC_URI = "file://${KERNEL_ITS_FILE}"

inherit deploy  siteinfo

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install[noexec] = "1"
do_populate_sysroot[noexec] = "1"
do_package[noexec] = "1"
do_packagedata[noexec] = "1"
do_package_write_ipk[noexec] = "1"
do_package_write_deb[noexec] = "1"
do_package_write_rpm[noexec] = "1"

KERNEL_IMAGE ?= "${KERNEL_IMAGETYPE}"
ROOTFS_IMAGE ?= "fsl-image-core"
KERNEL_ITB_IMAGE ?= "kernel.itb"
PACKAGE_ARCH = "${MACHINE_ARCH}"
MACHINE_BASE = "${@d.getVar('MACHINE', True).replace('-${SITEINFO_ENDIANNESS}', '')}"

do_fetch[nostamp] = "1"
do_unpack[nostamp] = "1"
do_deploy[nostamp] = "1"
do_deploy[depends] += "virtual/kernel:do_build ${ROOTFS_IMAGE}:do_build"

do_deploy () {
    install -m 644 ${WORKDIR}/${KERNEL_ITS_FILE} ${S}
    install -m 644 ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGE} ${S}/
    rm -f ${S}/${KERNEL_IMAGE}.gz
    gzip -c  ${S}/${KERNEL_IMAGE} > ${S}/${KERNEL_IMAGE}.gz
    install -m 644 ${DEPLOY_DIR_IMAGE}/${ROOTFS_IMAGE}-${MACHINE}.ext2.gz ${S}/
    install -d ${DEPLOYDIR}
    for DTS_FILE in ${KERNEL_DEVICETREE}; do
        DTB_IMAGE="${KERNEL_IMAGETYPE}-`basename ${DTS_FILE}`";
        cp  ${S}/${KERNEL_ITS_FILE}  ${S}/kernel.its
        sed -i -e "s,./arch/arm64/boot/Image.gz,${KERNEL_IMAGE}.gz," ${S}/kernel.its
        sed -i -e "s,./arch/arm64/boot/dts/freescale/fsl-${MACHINE}.dtb,${DTB_IMAGE}," ${S}/kernel.its
        sed -i -e "s,./fsl-image-${MACHINE_BASE}.ext2.gz,${ROOTFS_IMAGE}-${MACHINE}.ext2.gz," ${S}/kernel.its

        install -m 644 ${DEPLOY_DIR_IMAGE}/${DTB_IMAGE} ${S}/

        mkimage -f kernel.its  kernel-`basename ${DTS_FILE}`.itb
        install -m 644 ${S}/kernel-`basename ${DTS_FILE}`.itb ${DEPLOYDIR}/kernel-`basename ${DTS_FILE}`-${MACHINE}-${DATETIME}.itb
        cd ${DEPLOYDIR}
        ln -sf kernel-`basename ${DTS_FILE}`-${MACHINE}-${DATETIME}.itb kernel-`basename ${DTS_FILE}`-${MACHINE}.itb 
        cd -
    done
}

addtask deploy before build

COMPATIBLE_MACHINE = "(ls1043ardb|ls1043aqds|ls2080ardb)"
