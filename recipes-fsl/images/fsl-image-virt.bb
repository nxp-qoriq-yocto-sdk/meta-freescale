# Copyright (C) 2012-2015 Freescale Semiconductor Inc.

require recipes-fsl/images/fsl-image-core.bb

IMAGE_INSTALL += " \
    kernel-image \
    packagegroup-fsl-virtualization \
"

IMAGE_FSTYPES = "tar.gz ext2.gz ext2.gz.u-boot"

# copy rootfs image into rootfs
inherit fsl-utils
ROOTFS_POSTPROCESS_COMMAND += "rootfs_copy_core_image;"

do_rootfs[depends] += "fsl-image-core:do_rootfs"
