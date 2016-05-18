DESCRIPTION = "DPAA2 Resource Manager Tool"
SECTION = "dpaa2"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=e613b54e73c0e4a2a8918c61924bd623"

RDEPENDS_${PN} = "bash"

SRC_URI = "git://git.freescale.com/ppc/sdk/restool.git;branch=sdk-v2.0.x"
SRCREV = "eba25cdfdccef45f7ba02dd2f829b9b55198fbb1"

S = "${WORKDIR}/git"

do_compile_prepend () {
    unset CFLAGS
}

do_install () {
    oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(ls2080ardb)"
