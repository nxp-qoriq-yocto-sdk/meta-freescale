FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# We need this patch to allow aarch64 without crashes later in, e.g., polkitd
# This patch is tied to the kernel configuration for page tables.
# I do not know how to automatically tie it to the kernel configuration in
# Yocto, which of course would be preferable.
SRC_URI += "\
    file://mozjs-aarch64-fix.patch;patchdir=../../ \
    file://mozjs-powerpcembedded-fix.patch;patchdir=../../ \
"

