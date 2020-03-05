DESCRIPTION = "User-Space Data-Path Acceleration Architecture Drivers"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=39e58bedc879163c9338596e52df5b1f"
PR = "r4"

inherit pkgconfig

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS += "flib"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-yocto-sdk/usdpaa.git;nobranch=1;protocol=http"
SRCREV = "940d8f7f989c567b8c554ddba7bc4f0f4f21eb11"

S = "${WORKDIR}/git"

WRAP_ARCH ?= "${TARGET_ARCH}"
WRAP_ARCH_fsl-lsch2 = "arm64"
EXTRA_OEMAKE = 'CC="${CC}" LD="${LD}" AR="${AR}" ARCH="${WRAP_ARCH}"'

do_install () {
    oe_runmake install LIBDIR=${BASELIB} DESTDIR=${D}
}

PARALLEL_MAKE_pn-${PN} = ""
COMPATIBLE_MACHINE = "(qoriq-ppc|fsl-lsch2)"
