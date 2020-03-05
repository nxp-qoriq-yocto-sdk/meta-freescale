DESCRIPTION = "Ethernet Configuration Files"
SECTION = "eth-config"
LICENSE = "BSD & GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ed5eddbfbb84af5089ea94c382d423c"

PR = "r2"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-yocto-sdk/eth-config.git;nobranch=1;protocol=http"
SRCREV = "c1a4b3ae8e2bb6e5abe4d316e5d1f339085e8156"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "D=${D}"

do_install() {
    oe_runmake install
    chown -R root:root ${D}/etc
}

CLEANBROKEN = "1"
