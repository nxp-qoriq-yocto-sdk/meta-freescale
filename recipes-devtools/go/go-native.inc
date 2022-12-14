inherit native

export GOOS = "${BUILD_GOOS}"
export GOARCH = "${BUILD_GOARCH}"
export GOROOT_FINAL = "${STAGING_LIBDIR_NATIVE}/go"
export CGO_ENABLED = "1"

do_configure() {
    :
}

do_compile() {
    export GOBIN="${B}/bin"
    rm -rf ${GOBIN}
    mkdir ${GOBIN}
    cd src
    ./make.bash --host-only
}


make_wrapper() {
    rm -f ${D}${bindir}/$2
    cat <<END >${D}${bindir}/$2
#!/bin/bash
here=\`dirname \$0\`
export GOROOT="${GOROOT:-\`readlink -f \$here/../lib/go\`}"
\$here/../lib/go/bin/$1 "\$@"
END
    chmod +x ${D}${bindir}/$2
}

do_install() {
    install -d ${D}${libdir}/go
    cp -a ${B}/pkg ${D}${libdir}/go/
    install -d ${D}${libdir}/go/src
    (cd ${S}/src; for d in *; do \
        [ -d $d ] && cp -a ${S}/src/$d ${D}${libdir}/go/src/; \
    done)
    install -d ${D}${bindir} ${D}${libdir}/go/bin
    for f in ${B}/bin/*
    do
        base=`basename $f`
        install -m755 $f ${D}${libdir}/go/bin
        make_wrapper $base $base
    done
}

do_package[noexec] = "1"
do_packagedata[noexec] = "1"
do_package_write_ipk[noexec] = "1"
do_package_write_deb[noexec] = "1"
do_package_write_rpm[noexec] = "1"
