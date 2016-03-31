SUMMARY = "High-level API for the libsolv library"
HOMEPAGE = "https://github.com/rpm-software-management/hawkey"
BUGTRACKER = "https://github.com/rpm-software-management/hawkey/issues"
SECTION = "devel"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = "libsolv libcheck"
RDEPENDS_${PN} = "libsolv libcheck"

PV = "0.6.3"

SRC_URI = " \
        git://github.com/rpm-software-management/hawkey.git;protocol=https \
        file://fixmanpage.patch \
        file://fixpython.patch \
        file://fixlibdir.patch \
"
SRCREV = "08f43547a0bd3d40d8a7be7e2fff2eb87a8a7b71"
UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>\d+(\.\d+)+)"

S = "${WORKDIR}/git"

inherit cmake pythonnative

OECMAKE_SOURCEPATH = "${S}"
OECMAKE_BUILDPATH = "${WORKDIR}/build-${TARGET_ARCH}"
EXTRA_OECMAKE = "-DLIB_INSTALL_DIR=${baselib} -DPYTHON_INSTALL_DIR=${PYTHON_SITEPACKAGES_DIR} "

PACKAGES =+ "python-${PN} python-${PN}-dbg"

RDEPENDS_python-${PN} = "${PN}"

FILES_python-${PN}-dbg = " \
        ${PYTHON_SITEPACKAGES_DIR}/hawkey/.debug \
        ${PYTHON_SITEPACKAGES_DIR}/hawkey/test/.debug \
"
FILES_python-${PN} = " \
        ${PYTHON_SITEPACKAGES_DIR}/hawkey/*.so \
        ${PYTHON_SITEPACKAGES_DIR}/hawkey/*.py \
        ${PYTHON_SITEPACKAGES_DIR}/hawkey/test/*.so \
        ${PYTHON_SITEPACKAGES_DIR}/hawkey/test/*.py \
"
PROVIDES += "python-${PN} python-${PN}-dbg"

BBCLASSEXTEND =+ "native nativesdk"
