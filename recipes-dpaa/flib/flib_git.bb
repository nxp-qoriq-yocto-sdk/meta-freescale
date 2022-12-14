DESCRIPTION = "Foundation Library"
SECTION = "flib"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=75d2f6a74299640c05ae6c69ed7a4ad6"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-yocto-sdk/flib.git;nobranch=1;protocol=http"
SRCREV = "08e2aeb7d5c72ebbef30277ab4e73ab147c4c0c7"

S = "${WORKDIR}/git"

do_install(){
    oe_runmake install DESTDIR=${D}
}

ALLOW_EMPTY_${PN} = "1"
