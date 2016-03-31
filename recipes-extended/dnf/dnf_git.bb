SUMMARY = "Package Manager replacement for yum"
HOMEPAGE = "https://github.com/rpm-software-management/dnf.git"
BUGTRACKER = "https://github.com/rpm-software-management/dnf.git"
SECTION = "devel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "python-sphinx"
RDEPENDS_${PN} = "python bash hawkey"

PV = "1.1.6+git${SRCPV}"

SRC_URI = " \
        git://github.com/rpm-software-management/dnf.git;protocol=https \
        file://fixpython.patch \
        file://fixsysvinit.patch \
"
SRCREV = "6814739d9cd506284787749a78427937d139f558"
UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>\d+(\.\d+)+)"

S = "${WORKDIR}/git"

inherit autotools cmake pythonnative

EXTRA_OECMAKE = "-DWITH_MAN=0 -DPYTHON_INSTALL_DIR=${PYTHON_SITEPACKAGES_DIR} -DNO_SYSTEMD=1 -DNO_TMPFILES_D=1"

FILES_${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}/"

BBCLASSEXTEND = "native nativesdk"
