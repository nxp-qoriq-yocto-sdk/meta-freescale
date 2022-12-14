SUMMARY = "Simple x11vnc Init Script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"
SECTION = "x11"

SRC_URI = "\
    file://x11vnc \
    file://x11vnc.conf \
    file://x11vnc.service \
"
S = "${WORKDIR}"

inherit allarch update-rc.d systemd

INITSCRIPT_NAME = "x11vnc"
INITSCRIPT_PARAMS = "start 81 5 . stop 79 0 1 2 3 6 ."
INITSCRIPT_PARAMS_shr = "start 91 5 . stop 89 0 1 2 3 6 ."

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install x11vnc ${D}${sysconfdir}/init.d

    if ${@base_contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${sysconfdir}/default
        install -d ${D}${systemd_unitdir}/system
        install x11vnc.conf ${D}${sysconfdir}/default/x11vnc
        install -m 0644 ${WORKDIR}/x11vnc.service ${D}${systemd_unitdir}/system
    fi
}

RDEPENDS_${PN} = "x11vnc"

RPROVIDES_${PN} += "${PN}-systemd"
RREPLACES_${PN} += "${PN}-systemd"
RCONFLICTS_${PN} += "${PN}-systemd"
SYSTEMD_SERVICE_${PN} = "x11vnc.service"

FILES_${PN} += "${sysconfdir}/default/x11vnc"

