DESCRIPTION = "User-Space Data-Path Acceleration Architecture Demo Applications"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=d2a5d894118910d49993347f3f6e0f1e"

inherit pkgconfig

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "libxml2 libedit ncurses readline fmc usdpaa dpa-offload libnl"
DEPENDS_append_b4860qds = " ipc-ust"
DEPENDS_append_b4420qds = " ipc-ust"

RDEPENDS_${PN} = "libgcc bash"
RDEPENDS_${PN}_append_b4860qds = " ipc-ust"
RDEPENDS_${PN}_append_b4420qds = " ipc-ust"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-yocto-sdk/usdpaa-apps.git;nobranch=1;protocol=http"
SRCREV = "835cbceb4f76de1b493fea5dbe43a90f516b6f35"

S = "${WORKDIR}/git"

WRAP_ARCH ?= "${TARGET_ARCH}"
WRAP_ARCH_fsl-lsch2 = "arm64"
EXTRA_OEMAKE = 'CC="${CC}" LD="${LD}" AR="${AR}" ARCH="${WRAP_ARCH}"'

SOC ?= "P4080"
SOC_b4 = "B4860"
SOC_t1 = "T1040"
SOC_t2 = "T2080"
SOC_t4 = "T4240"
SOC_p1023rdb = "P1023"
SOC_fsl-lsch2 = "LS1043"

FMAN_VARIANT ?= "P4080"
FMAN_VARIANT_b4 = "FMAN_V3H"
FMAN_VARIANT_t1 = "FMAN_V3L"
FMAN_VARIANT_t2 = "FMAN_V3H"
FMAN_VARIANT_t4 = "FMAN_V3H"
FMAN_VARIANT_p1023rdb = "P1023"
FMAN_VARIANT_fsl-lsch2 = "LS1043"

do_compile_prepend () {
    export SOC=${SOC}
    export FMC_EXTRA_CFLAGS="-I ${STAGING_INCDIR}/fmc"
    export FMLIB_EXTRA_CFLAGS="-I ${STAGING_INCDIR}/fmd \
        -I ${STAGING_INCDIR}/fmd/Peripherals \
        -I ${STAGING_INCDIR}/fmd/integrations \
        -D${FMAN_VARIANT}"
    export USDPAA_EXTRA_CFLAGS="-I ${STAGING_INCDIR}/usdpaa"
    export DPAOFFLOAD_EXTRA_CFLAGS="-I ${STAGING_INCDIR}/dpa-offload"
    export LIBNL_EXTRA_CFLAGS="-I ${STAGING_INCDIR}/libnl3"
    export LIBNL_EXTRA_LDFLAGS="-lnl-3 -lnl-route-3"
    export LIBXML2_CFLAGS="`pkg-config --cflags libxml-2.0`"
    export LIBXML2_LDFLAGS="`pkg-config --libs --static libxml-2.0`"
    export LIBEDIT_CFLAGS="`pkg-config --cflags libedit`"
    export LIBEDIT_LDFLAGS="`pkg-config --libs --static libedit`"
}

do_install () {
    export SOC=${SOC}
    oe_runmake install LIBDIR=${BASELIB} DESTDIR=${D}
}

PARALLEL_MAKE_pn-${PN} = ""
FILES_${PN} += "/root/SOURCE_THIS /usr/etc/"
COMPATIBLE_MACHINE = "(qoriq-ppc|fsl-lsch2)"
