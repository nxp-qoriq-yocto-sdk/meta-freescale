DESCRIPTION = "A barebones image that contains a small package set to \
boot up. It is intended as a starting point for product development."

LICENSE = "MIT"

require recipes-core/images/core-image-minimal.bb

PACKAGE_ARCH = "${MACHINE_ARCH}"

CORE_IMAGE_EXTRA_INSTALL += "udev-extraconf lsb"
CORE_IMAGE_EXTRA_INSTALL_append_qoriq-ppc = " udev-rules-qoriq"
CORE_IMAGE_EXTRA_INSTALL_append_fsl-lsch2 = " udev-rules-qoriq"
CORE_IMAGE_EXTRA_INSTALL_append_imx = " udev-rules-imx"

IMAGE_FSTYPES = "tar.gz ext2.gz ext2.gz.u-boot jffs2 ubi"
