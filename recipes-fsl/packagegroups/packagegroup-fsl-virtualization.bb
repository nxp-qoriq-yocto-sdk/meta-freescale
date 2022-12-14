# Copyright (C) 2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Freescale Package group for vitualization"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

PACKAGES = "${PN}"

RDEPENDS_${PN} = "${@base_contains('DISTRO_FEATURES', 'x11', \
    'libvirt \
    libvirt-libvirtd \
    libvirt-virsh \
    lxc \
    qemu', \
    '', d)} \
" 

DOCKER_PKGS = " \
    docker \
    docker-registry \
"

RDEPENDS_${PN}_append_ls1043aqds = "${DOCKER_PKGS}"
RDEPENDS_${PN}_append_ls1043ardb = "${DOCKER_PKGS}"
RDEPENDS_${PN}_append_ls2080ardb = "${DOCKER_PKGS}"
