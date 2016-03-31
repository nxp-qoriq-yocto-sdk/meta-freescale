SUMMARY = "A package that drastically simplifies the fetching of files"
SECTION = "devel/python"
HOMEPAGE = "http://urlgrabber.baseurl.org/"
PRIORITY = "optional"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=68ad62c64cc6c620126241fd429e68fe"

DEPENDS = "python-pycurl-native"
RDEPENDS_${PN} = "python-pycurl"

inherit distutils pythonnative

SRC_URI = "git://yum.baseurl.org/urlgrabber.git"
SRCREV = "dc65f9dd96659b61ca91ae09e4729e18949251ce"

S = "${WORKDIR}/git"

FILES_${PN} += "${exec_prefix}/share/libexec"
