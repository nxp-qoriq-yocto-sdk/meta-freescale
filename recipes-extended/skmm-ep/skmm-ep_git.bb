DESCRIPTION = "SKMM application for PCIe endpoint"
SECTION = "skmm-ep"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=39e58bedc879163c9338596e52df5b1f"

DEPENDS = "libedit openssl virtual/kernel"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-yocto-sdk/skmm-ep.git;nobranch=1;protocol=http \
    file://add-two-missing-header-files.patch \
"
SRCREV = "bbf1ba00fcf407252a4c2e56783e63861088f9cb"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'ARCH=${TARGET_ARCH} MACHINE=${MACHINE}'

export LIBEDIT_CFLAGS = "`pkg-config --cflags libedit`"
export LIBEDIT_LDFLAGS = "`pkg-config --libs --static libedit`"

do_compile () {
	oe_runmake
}

do_install () {
	oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(p4080ds|t4240qds|c293pcie)"
