DESCRIPTION = "User-Space Data-Path Acceleration Architecture Drivers"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=39e58bedc879163c9338596e52df5b1f"
PR = "r4"

inherit pkgconfig

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS += "flib"

SRC_URI = "git://git.freescale.com/ppc/sdk/usdpaa.git;branch=sdk-v1.9.x"
SRCREV = "b73546dc923d9f3df92af08c2ebaf36d83ab96ea"

S = "${WORKDIR}/git"

WRAP_ARCH ?= "${TARGET_ARCH}"
WRAP_ARCH_fsl-lsch2 = "arm64"
EXTRA_OEMAKE = 'CC="${CC}" LD="${LD}" AR="${AR}" ARCH="${WRAP_ARCH}"'

do_install () {
    oe_runmake install LIBDIR=${BASELIB} DESTDIR=${D}
}

PARALLEL_MAKE_pn-${PN} = ""
COMPATIBLE_MACHINE = "(qoriq-ppc|fsl-lsch2)"
