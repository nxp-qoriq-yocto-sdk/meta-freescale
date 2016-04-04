require gdb-fsl.inc

SRC_URI += "file://0001-make-man-install-relative-to-DESTDIR.patch"

DEPENDS += "flex-native bison-native"
