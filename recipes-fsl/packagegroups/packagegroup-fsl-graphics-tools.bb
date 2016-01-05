# Copyright (C) 2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Freescale Package group for graphics tools"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


inherit packagegroup

RDEPENDS_${PN} = " \
    evtest \
    fbset \
    ${@base_contains('DISTRO_FEATURES', 'x11', 'v4l-utils', '', d)} \
"

SOC_GPU_TOOLS_X11 = ""
SOC_GPU_TOOLS_X11_append_mx6q  = " eglinfo-x11"
SOC_GPU_TOOLS_X11_append_mx6dl = " eglinfo-x11"
SOC_GPU_TOOLS_X11_append_mx6sx = " eglinfo-x11"

SOC_GPU_TOOLS_FB = ""
SOC_GPU_TOOLS_FB_mx6q  = "eglinfo-fb"
SOC_GPU_TOOLS_FB_mx6dl = "eglinfo-fb"
SOC_GPU_TOOLS_FB_mx6sx = "eglinfo-fb"

SOC_GPU_TOOLS_WAYLAND = ""

SOC_GPU_TOOLS_DIRECTFB = ""

RDEPENDS_${PN}_append_imx = " \
    ${@base_contains("LICENSE_FLAGS_WHITELIST", "commercial", "opencv-samples", "", d)} \
    ${@base_contains("DISTRO_FEATURES", "x11", "${SOC_GPU_TOOLS_X11}", \
       base_contains("DISTRO_FEATURES", "wayland", "${SOC_GPU_TOOLS_WAYLAND}", \
       base_contains("DISTRO_FEATURES", "directfb", "${SOC_GPU_TOOLS_DIRECTFB}", \
      "${SOC_GPU_TOOLS_FB}", d), d), d)} \
"
