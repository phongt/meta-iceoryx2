SUMMARY = "publishsubscribe"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit cmake

# this dependency is for the static libs
DEPENDS = " iceoryx2bindings"

# this dependency is for the shared libs
RDEPENDS:${PN} += "iceoryx2bindings"

S = "${WORKDIR}"
SRC_URI = " \
    file://CMakeLists.txt \
    file://src/publisher.c \
    file://src/subscriber.c \
    file://src/transmission_data.h \
"
do_install() {
  install -d ${D}${bindir}
  install -m 755 ${S}/build/example_c_publisher ${D}${bindir}
  install -m 755 ${S}/build/example_c_subscriber ${D}${bindir}
}
