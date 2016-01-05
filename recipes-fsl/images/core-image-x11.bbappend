require recipes-fsl/images/fsl-image-minimal.bb

IMAGE_FEATURES_append_qoriq = " ssh-server-openssh"
IMAGE_INSTALL_append_qoriq = " \
    alsa-utils \
    can-utils \
    iproute2 \
    lmsensors-sensors \
    mtd-utils \
    nfs-utils \
    openssh-sftp-server \
    strongswan \
"

export IMAGE_BASENAME = "core-image-x11"
IMAGE_FSTYPES_qoriq = "ext2.gz.u-boot"
