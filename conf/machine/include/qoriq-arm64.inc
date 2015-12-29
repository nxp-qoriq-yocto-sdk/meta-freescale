require conf/machine/include/qoriq-base.inc
require conf/machine/include/arm/arch-arm64.inc

KERNEL_IMAGETYPE = "Image"

MKUBIFS_ARGS ??= "-m 512 -e 15360 -c 3600"
UBINIZE_ARGS ??= "-m 512 -p 16KiB -s 512"

MACHINEOVERRIDES .= ":qoriq-arm64"
