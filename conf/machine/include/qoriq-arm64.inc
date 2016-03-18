require conf/machine/include/qoriq-base.inc
require conf/machine/include/arm/arch-arm64.inc

KERNEL_IMAGETYPE = "Image"

MKUBIFS_ARGS ??= "-m 512 -e 15360 -c 12000"
UBINIZE_ARGS ??= "-m 512 -p 16KiB -s 512"

BBMASK = ".*openjre|.*openjdk"

PREFERRED_VERSION_docker = "1.9.0%"

MACHINEOVERRIDES .= ":qoriq-arm64"
