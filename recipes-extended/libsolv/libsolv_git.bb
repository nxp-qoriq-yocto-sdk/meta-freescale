SUMMARY = "Library for solving packages and reading repositories"
HOMEPAGE = "https://github.com/openSUSE/libsolv"
BUGTRACKER = "https://github.com/openSUSE/libsolv/issues"
SECTION = "devel"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.BSD;md5=62272bd11c97396d4aaf1c41bc11f7d8"

DEPENDS = "expat zlib rpm swig-native python-sphinx"

PV = "0.6.19"

SRC_URI = "git://github.com/openSUSE/libsolv.git \
    file://fixpythoninstall.patch \
"
SRCREV = "4c5af401a89858d4cebbfe40c59a0031ff9db5b0"
UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>\d+(\.\d+)+)"

S = "${WORKDIR}/git"

inherit cmake pythonnative

EXTRA_OECMAKE = " \
    -DLIB=${baselib} \
    -DPYTHON_INSTALL_DIR=${PYTHON_SITEPACKAGES_DIR} \
    -DENABLE_PYTHON=ON \
    -DENABLE_RPMDB=ON \
    -DENABLE_RPMMD=ON \
    -DRPM5=ON \
"
export BUILD_SYS
export HOST_SYS
export STAGING_LIBDIR
export STAGING_INCDIR

PACKAGES =+ "${PN}-tools ${PN}ext python-solv python-solv-dbg"

FILES_${PN}-dev += "${datadir}/cmake/Modules/FindLibSolv.cmake"
FILES_${PN}-tools = "${bindir}/*"
FILES_${PN}ext = "${libdir}/${PN}ext.so.*"

PROVIDES += "python-solv python-solv-dbg"
RDEPENDS_python-solv = "${PN}"
FILES_python-solv-dbg = "${PYTHON_SITEPACKAGES_DIR}/.debug/_solv.so"
FILES_python-solv = "${PYTHON_SITEPACKAGES_DIR}/*solv.*"

BBCLASSEXTEND =+ "native nativesdk"
