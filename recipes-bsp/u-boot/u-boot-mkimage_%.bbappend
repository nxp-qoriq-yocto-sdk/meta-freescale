do_install_append_fsl-qoriq () {
    install -m 0755 tools/mkenvimage ${D}${bindir}/uboot-mkenvimage
    ln -sf uboot-mkenvimage ${D}${bindir}/mkenvimage
}

