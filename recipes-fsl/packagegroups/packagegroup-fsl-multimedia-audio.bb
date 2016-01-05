# Copyright (C) 2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Package group used by freescale to add packages which provides audio support"
SUMMARY = "Freescale Package group for mutlimedia audio"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES += " \
    ${@bb.utils.contains("MACHINE_FEATURES", "alsa", "${PN}-alsa", "", d)} \
"

RDEPENDS_${PN} = "\
    ${@bb.utils.contains('COMBINED_FEATURES', 'alsa', '${PN}-alsa', '',d)} \
"

# Alsa related packages
MACHINE_ALSA_PACKAGES ?= ""
RDEPENDS_${PN}-alsa = " \
    alsa-lib \
    packagegroup-base-alsa \
    ${MACHINE_ALSA_PACKAGES} \
"

DESCRIPTION_imx = "Freescale Package group for mutlimedia audio specific to i.MX."

# i.MX specific Plugins
MACHINE_ALSA_PACKAGES_mx6 = "${@base_contains('DISTRO_FEATURES', 'alsa', 'fsl-alsa-plugins', '', d)}"
MACHINE_ALSA_PACKAGES_mx7 = "${@base_contains('DISTRO_FEATURES', 'alsa', 'fsl-alsa-plugins', '', d)}"
