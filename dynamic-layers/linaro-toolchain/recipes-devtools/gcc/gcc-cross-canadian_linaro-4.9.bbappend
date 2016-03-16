FILESEXTRAPATHS_prepend := "${THISDIR}/gcc-${PV}:"
SRC_URI_append = "file://Fix-large-integer-type-issue-for-aarch64-small-model.patch"

INSANE_SKIP_${PN} += "libdir"
INSANE_SKIP_${PN}-dbg += "libdir"
