require fsl-image-core.bb

SUMMARY = "Large image to be used for development and evaluation"
DESCRIPTION = "Large image which includes all the tested tools and \
Freescale-specific packages. It is a full Linux system rather than \
an embedded system for development and evaluation tasks."

LICENSE = "MIT"

# copy the manifest and the license text for each package to image
COPY_LIC_MANIFEST = "1"

IMAGE_INSTALL_append = " \
    packagegroup-core-buildessential \
    packagegroup-core-eclipse-debug \
    packagegroup-core-full-cmdline \
    packagegroup-core-nfs-server \
    packagegroup-core-tools-debug \
    packagegroup-fsl-networking-extended \
    packagegroup-fsl-tools-audio \
    packagegroup-fsl-virtualization \
    packagegroup-fsl-devtools \
    packagegroup-fsl-benchmark-extended \
    packagegroup-fsl-tools-extended \
"

export IMAGE_BASENAME = "fsl-image-full"

IMAGE_FEATURES_imx += " splash"
IMAGE_FEATURES_imx += "${@base_contains('DISTRO_FEATURES', 'x11', \
                            ' package-management x11-base x11-sato hwcodecs', '', d)}"
IMAGE_FEATURES_imx += "${@base_contains('DISTRO_FEATURES', 'wayland', \
                            base_contains('DISTRO_FEATURES', 'x11', '', ' package-management hwcodecs', d), \
                            '', d)}"

X11_EXTRA_IMAGE_FEATURES ?= "" 
X11_EXTRA_IMAGE_FEATURES_imx ?= "${@base_contains('DISTRO_FEATURES', 'x11', \
    ' tools-testapps', '', d)}"

# Add extra image features
EXTRA_IMAGE_FEATURES_imx += " \
    ${X11_EXTRA_IMAGE_FEATURES} \
    nfs-server \
    tools-debug \
    tools-profile \
    ssh-server-dropbear \
"

IMAGE_INSTALL_append_imx = " \
    packagegroup-fsl-graphics-minimal \
    packagegroup-fsl-graphics-tools \
    packagegroup-fsl-graphics-demos \
    packagegroup-fsl-graphics-benchmarks \
    packagegroup-fsl-multimedia-audio \
    packagegroup-fsl-multimedia-gstreamer1.0-core \
    packagegroup-fsl-multimedia-gstreamer1.0-testapps \
"

IMAGE_FSTYPES_qoriq = "tar.gz"
