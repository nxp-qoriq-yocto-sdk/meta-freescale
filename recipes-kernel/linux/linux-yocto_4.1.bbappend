FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

inherit qoriq_build_64bit_kernel

KBRANCH_qoriq = "sdk-v2.0.x"
SRCREV_machine_qoriq = "36311a9ec4904c080bbdfcefc0f3d609ed508224"
SRCREV_meta-fsl_qoriq = "${AUTOREV}"

SRC_URI_qoriq = "git://sw-stash.freescale.net/scm/sdk/linux-sdk2.0.git;name=machine;branch=${KBRANCH};protocol=http \
    git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-4.1;destsuffix=${KMETA} \
    git://sw-stash.freescale.net/scm/sdk/yocto-kernel-cache.git;type=kmeta;name=meta-fsl;branch=master;destsuffix=${KMETA}-fsl;protocol=http \
"

KERNEL_EXTRA_FEATURES_qoriq = ""
KERNEL_FEATURES_remove_qoriq = "features/kvm/qemu-kvm-enable.scc"

# add features to be enabled in KERNEL_FEATURES
# KERNEL_FEATURES_append_qoriq = "features/kgdb/kgdb.scc"

KCONFIG_MODE_qoriq = "--alldefconfig"

KCONF_BSP_AUDIT_LEVEL_qoriq = "1"

COMPATIBLE_MACHINE_qoriq = "qoriq"
