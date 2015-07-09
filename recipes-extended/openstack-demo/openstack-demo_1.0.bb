DESCRIPTION = "Openstack demo scripts and configure files"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

SRC_URI = " file://Havana_conf \
    file://openstack_deploy_T4.sh \
"

S = "${WORKDIR}"

do_install () {
    install -d ${D}${ROOT_HOME}
    cp -a ${WORKDIR}/Havana_conf ${D}${ROOT_HOME}
    install -m 0755 ${WORKDIR}/openstack_deploy_T4.sh ${D}${ROOT_HOME}
}

FILES_${PN} += "${ROOT_HOME}"
COMPATIBLE_MACHINE = "(qoriq-ppc)"
