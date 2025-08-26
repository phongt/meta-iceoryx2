SUMMARY = "iceoryx2 C++ examples"
DESCRIPTION = "This package contains the iceoryx2 C++ examples"
HOMEPAGE = "https://iceoryx.io"
BUGTRACKER = "https://github.com/eclipse-iceoryx/iceoryx2/issues"
LICENSE = "Apache-2.0 | MIT"
LIC_FILES_CHKSUM = "file://LICENSE-APACHE;md5=22a53954e4e0ec258dfce4391e905dac \
                    file://LICENSE-MIT;md5=b377b220f43d747efdec40d69fcaa69d"

inherit cmake

DEPENDS = " \
  iceoryx-hoofs-subset \
  iceoryx2-cmake-modules \
  iceoryx2-cxx \
  "

SRC_URI = "git://github.com/eclipse-iceoryx/iceoryx2.git;protocol=https;branch=main"
SRCREV = "1685d7d7a9759e92464782ff14a7d6418b033f28"

S = "${WORKDIR}/git/examples/cxx"

RDEPENDS:${PN}-dev += "iceoryx2-cxx-staticdev iceoryx-hoofs-subset-dev"
RDEPENDS:${PN} += "iceoryx-hoofs-subset iceoryx2-cxx"
BBCLASSEXTEND = "native nativesdk"

do_install() {
  install -d ${D}${bindir}/iceoryx2/examples/cxx

  for example in $(find ${B} -maxdepth 2 -type f -executable); do
    install -m 0755 $example ${D}${bindir}/iceoryx2/examples/cxx
  done
}
