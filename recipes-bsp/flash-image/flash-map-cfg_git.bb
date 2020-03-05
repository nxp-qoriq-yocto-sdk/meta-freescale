DESCRIPTION = "flash map config"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8fbb14cbf822c224cb71a80a0e097452"

inherit deploy

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-yocto-sdk/flashmap.git;nobranch=1;protocol=http"
SRCREV= "decedc0186a4ba1483ee0ea488482d3ca75ea39f"

S = "${WORKDIR}/git"

do_install () {
	install -d ${D}/${datadir}/flashmap
	install -m 644 ${MACHINE}/*.cfg  ${D}/${datadir}/flashmap 
}

do_deploy () {
	install -d ${DEPLOYDIR}/flashmap
	install -m 644 ${MACHINE}/*.cfg ${DEPLOYDIR}/flashmap/
}
addtask deploy before do_build after do_install

FILES_${PN} = "${datadir}/*"
COMPATIBLE_MACHINE = "(ls1043aqds|ls1043ardb|ls1043ardb-be|ls2080ardb)"
