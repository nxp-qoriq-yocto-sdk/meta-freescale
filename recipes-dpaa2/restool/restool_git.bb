DESCRIPTION = "DPAA2 Resource Manager Tool"
SECTION = "dpaa2"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=e613b54e73c0e4a2a8918c61924bd623"

RDEPENDS_${PN} = "bash"

SRC_URI = "git://sw-stash.freescale.net/scm/dpaa2/restool.git;branch=sdk-v2.0.x;protocol=http"
SRCREV = "04ed2cc28dcc1f619bb29353a5c820ac2d50bd41"

S = "${WORKDIR}/git"

do_compile_prepend () {
    unset CFLAGS
}

do_install () {
    oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(ls2080ardb)"
