FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Xvfb won't load the RANDR extension despite
# a contrary log without this patch

SRC_URI += "\
    file://xvfb-add-randr-support-v2.patch \
"
