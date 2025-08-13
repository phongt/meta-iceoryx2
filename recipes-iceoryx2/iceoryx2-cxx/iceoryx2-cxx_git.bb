SUMMARY = "iceoryx2 C++ bindings"
DESCRIPTION = "This package contains the iceoryx2 C++ bindings"
HOMEPAGE = "https://iceoryx.io"
BUGTRACKER = "https://github.com/eclipse-iceoryx/iceoryx2/issues"
LICENSE = "Apache-2.0 | MIT"
LIC_FILES_CHKSUM = "file://../../LICENSE-APACHE;md5=22a53954e4e0ec258dfce4391e905dac \
                    file://../../LICENSE-MIT;md5=b377b220f43d747efdec40d69fcaa69d"

inherit cmake

DEPENDS = " iceoryx-hoofs-subset iceoryx2-c"

SRC_URI = "git://github.com/eclipse-iceoryx/iceoryx2.git;protocol=https;branch=main \
           file://0001-Make-CXX-bindings-build-separately.patch \
           file://0002-Fix-iceoryx-hoofs-fetching.patch"
SRCREV = "7d2eaebb6c1baad7d50b3e7b13a90b412841a92b"

S = "${WORKDIR}/git/iceoryx2-ffi/cxx"

INSANE_SKIP:${PN} += " already-stripped"
FILES_SOLIBSDEV = ""

EXTRA_OECMAKE += " -DCMAKE_INSTALL_PREFIX=${D}/${exec_prefix}"

FILES:${PN}-staticdev += "${libdir}/libiceoryx2_cxx.a"
FILES:${PN} += "${libdir}/libiceoryx2_cxx.so"
RDEPENDS:${PN}-dev += "${PN}-staticdev iceoryx-hoofs-subset-dev"
RDEPENDS:${PN} += "iceoryx-hoofs-subset iceoryx2-c"
BBCLASSEXTEND = "native nativesdk"

do_install() {
  cmake --install ${S}/../../../build
}
