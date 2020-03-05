PV = "7.10.1+fsl"

SRC_URI = "file://gdb-fsl-sdk-v2.0.tar.bz2"

SRC_URI += "file://0002-Change-order-of-CFLAGS.patch \
            file://0001-Use-exported-definitions-of-SIGRTMIN.patch \
            file://0001-include-sys-types.h-for-mode_t.patch \
            file://0001-use-asm-sgidefs.h.patch \
            file://force-readline-static.patch \
           "
S = "${WORKDIR}/gdb-fsl-sdk-v2.0"

SRC_URI[md5sum] = "a609a1ed7a56d87b94fd1d7567e6757d"
SRC_URI[sha256sum] = "2a4d27f3d9f2eafb7fb2b1efeddc126b9487959c1afee920298e7ba4331d61d0"

EXTRA_OECONF += " --disable-binutils --disable-gas --disable-gold --disable-gprof --disable-ld "
