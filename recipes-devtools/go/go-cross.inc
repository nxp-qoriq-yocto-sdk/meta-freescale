inherit cross

DEPENDS += "go-native"

export GOHOSTOS = "${BUILD_GOOS}"
export GOHOSTARCH = "${BUILD_GOARCH}"
export GOOS = "${TARGET_GOOS}"
export GOARCH = "${TARGET_GOARCH}"
export GOARM = "${TARGET_GOARM}"
export GOROOT_BOOTSTRAP = "${STAGING_LIBDIR_NATIVE}/go"
export GOROOT_FINAL = "${libdir}/go"
export CGO_ENABLED = "1"
export CC_FOR_TARGET="${TARGET_PREFIX}gcc ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET}"
export CXX_FOR_TARGET="${TARGET_PREFIX}g++ ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET}"
CC = "${@d.getVar('BUILD_CC', True).strip()}"

do_configure() {
    :
}

do_compile() {
    export GOBIN="${B}/bin"
    rm -rf ${GOBIN} ${B}/pkg
    mkdir ${GOBIN}
    cd src
    ./make.bash --host-only
}

do_install() {
    install -d ${D}${libdir}/go
    cp -a ${B}/pkg ${D}${libdir}/go/
    install -d ${D}${libdir}/go/src
    (cd ${S}/src; for d in *; do \
        [ -d $d ] && cp -a ${S}/src/$d ${D}${libdir}/go/src/; \
    done)
    install -d ${D}${bindir}
    for f in ${B}/bin/*
    do
        install -m755 $f ${D}${bindir}
    done
}

do_package[noexec] = "1"
do_packagedata[noexec] = "1"
do_package_write_ipk[noexec] = "1"
do_package_write_deb[noexec] = "1"
do_package_write_rpm[noexec] = "1"
