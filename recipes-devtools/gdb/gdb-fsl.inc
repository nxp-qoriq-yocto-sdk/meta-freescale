PV = "7.10.1+fsl"

SRCBRANCH = "sdk-v2.0.x"
SRCREV = "f7faa4d1e984de441479f8e319bbec0b12ea0624"
SRC_URI = "git://git.freescale.com/ppc/sdk/gdb.git;branch=${SRCBRANCH};destsuffix=gdb-${PV}"

SRC_URI += "file://0002-Change-order-of-CFLAGS.patch \
            file://0001-Use-exported-definitions-of-SIGRTMIN.patch \
            file://0001-include-sys-types.h-for-mode_t.patch \
            file://0001-use-asm-sgidefs.h.patch \
            file://force-readline-static.patch \
           "

EXTRA_OECONF += " --disable-binutils --disable-gas --disable-gold --disable-gprof --disable-ld "
