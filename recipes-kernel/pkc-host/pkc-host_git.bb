DESCRIPTION = "pkc host driver"
SECTION = "pkc-host"
LICENSE = "BSD & GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=99803d8e9a595c0bdb45ca710f353813"

inherit  module qoriq_build_64bit_kernel
RDEPENDS_${PN} += "cryptodev-module bc"

# Currently pkc-host does not support RSA_KEYGEN, remove this
# if it is fixed.
REQUIRED_DISTRO_FEATURES = "c29x_pkc"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-yocto-sdk/pkc-host.git;nobranch=1;protocol=http"
SRCREV = "21a6b5b7393bd668a049bde457e1cd0aa05d1bd0"

S = "${WORKDIR}/git"

EXTRA_OEMAKE='KERNEL_DIR="${STAGING_KERNEL_DIR}" PREFIX="${D}"'

do_install() {
    oe_runmake INSTALL_MOD_PATH="${D}" modules_install
    install -d ${D}/etc/crypto
    install -d ${D}/${bindir}
    cp ${S}/crypto.cfg ${D}/etc/crypto
    cp ${S}/images/pkc-firmware.bin ${D}/etc/crypto
    cp ${S}/perf/c29x_driver_perf_profile.sh ${D}/${bindir}
}


FILES_${PN} = "${bindir}/cli \
    ${bindir}/c29x_driver_perf_profile.sh \
    /etc/crypto/crypto.cfg \
    /etc/crypto/pkc-firmware.bin \
"

COMPATIBLE_MACHINE = "(qoriq)"
