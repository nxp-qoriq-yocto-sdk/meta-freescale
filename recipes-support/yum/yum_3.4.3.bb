SUMMARY = "yum package manger is an automatic updater for rpm."
HOMEPAGE = "http://yum.baseurl.org/"
BUGTRACKER = "http://yum.baseurl.org/report"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=18810669f13b87348459e611d31ab760 \
                    file://yum/sqlutils.py;beginline=2;endline=14;md5=d704ae6a9d69ce90768ab9188236b992"

RDEPENDS_${PN} = "bash python-rpm python-core python-iniparse python-urlgrabber \
            python-shell python-re python-logging python-pickle \
        python-netserver python-compression \
        python-unixadmin python-xml python-sqlite3 \
        python-textutils python-fcntl python-email \
        python-pycurl \
        yum-metadata-parser"

SRC_URI = "http://yum.baseurl.org/download/3.4/yum-${PV}.tar.gz \
        file://fixsyspathsforsdk.patch \
        file://fixppcnomenclature.patch \
        file://skipelfbuildid.patch \
        file://rpm5.patch \
        file://sysvinitdir.patch \
"
SRC_URI[md5sum] = "7c8ea8beba5b4e7fe0c215e4ebaa26ed"
SRC_URI[sha256sum] = "0178f97820ced9bfbcc269e6fc3ea35e29e35e2d263d24c7bff8660ee62d37ca"

S = "${WORKDIR}/yum-${PV}"

do_install () {
    oe_runmake install DESTDIR=${D} SBINDIR=${sbindir} MANDIR=${mandir} INCLUDEDIR=${includedir}
}

do_compile_append () {
    sed -e 's#!/usr/bin/python#!${bindir}/python#' -e 's#/usr/share#${datadir}#' -i ${S}/bin/yum.py
    sed -e 's#!/usr/bin/python#!${bindir}/python#' -e 's#/usr/share#${datadir}#' -i ${S}/bin/yum-updatesd.py
}

PACKAGES += "${PN}-bash"
FILES_${PN}-bash += "/etc/cron.daily/0yum.cron /etc/init.d/yum.cron /etc/init.d/yum-updatesd.init"

FILES_${PN} += "${libdir}/python* ${datadir}/yum-cli"

#BBCLASSEXTEND = "native"
