PACKAGECONFIG_qoriq-ppc = "qemu yajl lxc test remote macvtap libvirtd netcf udev python"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append_qoriq-ppc = " file://qemu.conf"

do_install_append_qoriq-ppc() {
	install -m 0644 ${WORKDIR}/qemu.conf ${D}${sysconfdir}/libvirt/qemu.conf
}

inherit useradd
USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--system libvirt"

do_install_append() {
        sed -e "s:^#unix_sock_group =:unix_sock_group =:g" -i ${D}/etc/libvirt/libvirtd.conf
        sed -e "s:^#unix_sock_rw_perms =:unix_sock_rw_perms =:g" -i ${D}/etc/libvirt/libvirtd.conf
}

