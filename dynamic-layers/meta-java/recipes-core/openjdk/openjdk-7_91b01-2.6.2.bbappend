FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

ICEDTEAPATCHES_append_qoriq-ppc = " \
    file://icedtea-jdk-ppc64-jvm-cfg.patch;apply=no \
    file://icedtea-jdk-powerpc-atomic64.patch;apply=no \
    file://icedtea-jdk-sizers-crosscompile-hack.patch;apply=no \
"
DISTRIBUTION_PATCHES_append_qoriq-ppc = " \
    patches/icedtea-jdk-ppc64-jvm-cfg.patch \
    patches/icedtea-jdk-powerpc-atomic64.patch \
    patches/icedtea-jdk-sizers-crosscompile-hack.patch \
"
