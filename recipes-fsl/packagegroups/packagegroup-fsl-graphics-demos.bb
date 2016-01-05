# Copyright (C) 2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Package group used by freescale to provide set of packages for GPU test"
SUMMARY = "Freescale Package group for graphics demos"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


inherit packagegroup

# Wayland demos
WAYLAND_DEMOS = "${@base_contains('DISTRO_FEATURES', 'x11', '', \
       base_contains('DISTRO_FEATURES', 'wayland', \
          'weston-examples gtk+3-demo clutter-1.0-examples', '', d), d)}"

WAYLAND_DEMOS_remove_mx6sl = "clutter-1.0-examples"

# X11 demos that depend on OpenGL
X11_GL_DEMOS = "${@base_contains('DISTRO_FEATURES', 'x11 opengl', 'mesa-demos', '', d)}"

RDEPENDS_${PN} = " \
    packagegroup-fsl-graphics-core \
    ${WAYLAND_DEMOS} \
    ${X11_GL_DEMOS} \
"

GPU_DEMOS = ""
GPU_DEMOS_mx6 = " imx-gpu-viv-demos fsl-gpu-sdk"

RDEPENDS_${PN}_append_imx = " ${GPU_DEMOS}"

# FIXME: fsl-gpu-sdk is not supported for i.MX6 SoloLite due to lack of
# OpenVG support which is intended to be added in a future release. 
RDEPENDS_${PN}_remove_mx6sl = " \
    clutter-1.0-examples \
    fsl-gpu-sdk \
"
