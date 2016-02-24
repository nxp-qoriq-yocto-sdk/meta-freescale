# Copyright (C) 2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Freescale Package group for core tools"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

PACKAGES = "${PN}"

RDEPENDS_${PN} = " \
    e2fsprogs \
    e2fsprogs-badblocks \
    e2fsprogs-e2fsck \
    e2fsprogs-tune2fs  \
    i2c-tools \
    kmod \
    kernel-modules \
    libhugetlbfs \
    lmsensors-sensors \
    memtester \
    pkgconfig \
    python-subprocess \
    python-datetime \
    python-json \
    procps \
    minicom \
    coreutils \
    elfutils \
    file \
    psmisc \
    sysfsutils \
    sysklogd \
    sysstat \
"

DPAA_PKGS = " \
    eth-config \
    fmc \
    usdpaa \
    usdpaa-apps \
    dpa-offload \
    hypervisor-partman \
"
DPAA_PKGS_e500v2 = ""
DPAA_PKGS_ls102xa = ""
DPAA_PKGS_fsl-lsch3 = "restool"
DPAA_PKGS_remove_fsl-lsch2 = "hypervisor-partman usdpaa usdpaa-apps dpa-offload"
DPAA_PKGS_remove_e6500 = "hypervisor-partman"
DPAA_PKGS_append_e6500 = " lib64-hypervisor-partman"

IPC_PKGS_qoriq = " \
    ipc-module-multi \
    ipc-module-single \
    ipc-ust \
"

RDEPENDS_${PN}_append_qoriq = "\
    apptrk \
    asf \
    merge-files \
    ${DPAA_PKGS} \
"
# NOTE: Remove the conditional pkc-host inclusion and all traces of c29x_pkc
# DISTRO_FEATURE if pkc-host no longer requires customized cryptodev patches
RDEPENDS_${PN}_append_qoriq-ppc = "\
    ${@base_contains('DISTRO_FEATURES', 'c29x_pkc', 'pkc-host', '', d)} \
"
RDEPENDS_${PN}_remove_ls102xa ="apptrk"
RDEPENDS_${PN}_remove_fsl-lsch2 ="apptrk" 
RDEPENDS_${PN}_remove_fsl-lsch3 ="apptrk asf"
RDEPENDS_${PN}_append_e500v2 = " testfloat"
RDEPENDS_${PN}_append_c293pcie = " skmm-ep"
RDEPENDS_${PN}_append_p1023rdb = " fmc eth-config"
RDEPENDS_${PN}_append_b4860qds = "${IPC_PKGS}"
RDEPENDS_${PN}_append_b4420qds = "${IPC_PKGS}"

RDEPENDS_${PN}_append_p4080ds = " \
    skmm-ep \
    skmm-host \
"

RDEPENDS_${PN}_append_t1040 = " \
    uio-seville \
"

RDEPENDS_${PN}_append_t1042 = " \
"

RDEPENDS_${PN}_append_t4240qds = " \
    skmm-ep \
    skmm-host \
"
