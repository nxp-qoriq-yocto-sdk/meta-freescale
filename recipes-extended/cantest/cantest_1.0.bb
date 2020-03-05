DESCRIPTION = "cantest"
HOMEPAGE = "http://svn.berlios.de/svnroot/repos/socketcan/trunk/can-utils/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d85064f0fa931974668d281ab83cc97e"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-yocto-sdk/cantest.git;nobranch=1;protocol=http"
SRCREV = "0ad5fa86b1007aefef60a10ccb3a946497477995"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${bindir}
    install -m 755 cantest ${D}${bindir}
}

COMPATIBLE_MACHINE = "(e500v2)"
