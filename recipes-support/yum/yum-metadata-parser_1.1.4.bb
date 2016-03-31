DESCRIPTION = "C-based metadata parser to quickly parse xml metadata into sqlite databases."
HOMEPAGE = "http://yum.baseurl.org/"
BUGTRACKER = "http://yum.baseurl.org/report"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://yum-metadata-parser.spec;md5=b61a69a78f7178a713795c46b37da8a1"

DEPENDS = "python sqlite3 glib-2.0 libxml2"

SRC_URI = "git://yum.baseurl.org/yum-metadata-parser.git"
SRCREV = "9d97d382f631bcce856659c050d6b468c411ef3c"

S = "${WORKDIR}/git"

TARGET_CFLAGS += "-I${STAGING_LIBDIR}/glib-2.0"

inherit distutils
