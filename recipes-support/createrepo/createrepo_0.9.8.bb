SUMMARY = "Creates metadata indexes for RPM package repositories"
HOMEPAGE = "http://createrepo.baseurl.org/"
SECTION = "devel"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=18810669f13b87348459e611d31ab760"

DEPENDS_class-target += "libxml2-native rpm-native"
RDEPENDS_${PN}_class-target = "libxml2-python yum python-core"

SRC_URI = "http://createrepo.baseurl.org/download/createrepo-${PV}.tar.gz \
    file://pathfix.patch \
    file://rpm-createsolvedb.py \
"
SRC_URI[md5sum] = "e37418bebb559e7420532574c1bdb18c"
SRC_URI[sha256sum] = "736842bda7d404a03470775332ccda339a114f14772d7a354d99bf64f38ecd14"

S = "${WORKDIR}/createrepo-${PV}"

do_compile_append () {
    sed -e 's#exec /usr/share#exec ${datadir}#' -i ${S}/bin/createrepo
    sed -e 's#exec /usr/share#exec ${datadir}#' -i ${S}/bin/modifyrepo
    sed -e 's#!/usr/bin/python#!${bindir}/python#' -i ${S}/genpkgmetadata.py
    sed -e 's#!/usr/bin/python#!${bindir}/python#' -i ${S}/modifyrepo.py
}

do_install () {
    sed -e 's#PYTHON=python#PYTHON=nativepython#' -i ${S}/createrepo/Makefile
    sed -e 's#PYSYSDIR := $(shell $(PYTHON) -c .import sys; print sys.prefix.)#PYSYSDIR = $(exec_prefix)#' -i ${S}/createrepo/Makefile
    oe_runmake -e 'DESTDIR=${D}' install
    install -m 0755 ${WORKDIR}/rpm-createsolvedb.py ${D}${bindir}/
}

# We need to ship both lib and lib64 on powerpc64
FILES_${PN} += "${exec_prefix}/lib ${libdir}"
