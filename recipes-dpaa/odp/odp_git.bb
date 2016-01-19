SUMMARY = "DPAA Open Data Plane Interface Implementation"
DESCRIPTION = "OpenDataPlane (ODP) provides a data plane application programming \
        environment that is easy to use, high performance, and portable between networking SoCs."
HOMEPAGE = "http://www.opendataplane.org"
SECTION = "console/network"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4ccfa994aa96974cfcd39a59faee20a2"

DEPENDS = "openssl usdpaa fmlib libxml2"

SRC_URI = "git://sw-stash.freescale.net/scm/gitam/odp.git;branch=odp-v1.3.x;protocol=http"
SRCREV = "5c8caf10b8925664380762a5d1915e54a17cbb26"

S = "${WORKDIR}/git"

inherit autotools

RDEPENDS_${PN} = "bash libcrypto"

PACKAGECONFIG[perf] = "--enable-test-perf,,,"

do_configure () {
    export SOC=${ODP_SOC}
    ${S}/bootstrap
    ${S}/configure  --with-platform=${ODP_PLATFORM}  --host=${TARGET_SYS} --with-sdk-install-path=${STAGING_DIR_HOST} --disable-shared
}

do_compile_prepend () {
    export SOC=${ODP_SOC}
}

# ODP primary shipped as static library plus some API test and samples/
FILES_${PN}-dbg += "/usr/local/bin/.debug"
FILES_${PN}-staticdev += "/usr/local/lib/libodp.a /usr/local/lib/libodphelper.a"
FILES_${PN}-dev += "/usr/local/include /usr/local/lib"
FILES_${PN} += "/usr/local/bin /usr/local/share"

COMPATIBLE_MACHINE = "(ls1043ardb)"
