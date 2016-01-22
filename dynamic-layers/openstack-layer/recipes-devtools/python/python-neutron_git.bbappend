# both meta-openstack-compute-test-config and meta-openstack-controller-test-config
# layer has recipes-devtools/python/python-neutron_git.bbappend in which
# neutron-test-config is added into PACKAGES
# hardcode the PACKAGES here to avoid packages-list QA error which result in no rpm
# generated, which in turn fail the do_rootfs.
PACKAGES_qoriq = "python-neutron-staticdev python-neutron-dev python-neutron-dbg python-neutron-doc python-neutron       neutron-tests      neutron      neutron-doc      neutron-server      neutron-plugin-openvswitch      neutron-plugin-ml2      neutron-ml2      neutron-dhcp-agent      neutron-l3-agent      neutron-metadata-agent      neutron-extra-agents      neutron-setup      neutron-plugin-openvswitch-setup        neutron-test-config"
