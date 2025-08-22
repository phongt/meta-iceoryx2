SUMMARY = "iceoryx2 C++ bindings"
DESCRIPTION = "This package contains the iceoryx2 C++ bindings"
HOMEPAGE = "https://iceoryx.io"
BUGTRACKER = "https://github.com/eclipse-iceoryx/iceoryx2/issues"
LICENSE = "Apache-2.0 | MIT"
LIC_FILES_CHKSUM = "file://LICENSE-APACHE;md5=22a53954e4e0ec258dfce4391e905dac \
                    file://LICENSE-MIT;md5=b377b220f43d747efdec40d69fcaa69d"

inherit cmake

# NOTE:: googletest requires meta-openembedded/meta-oe
DEPENDS = " \
  iceoryx-hoofs-subset \
  iceoryx2-c \
  iceoryx2-cmake-modules \
  googletest \
  "

SRC_URI = "git://github.com/eclipse-iceoryx/iceoryx2.git;protocol=https;branch=main"
SRCREV = "af5669e46ebc2460209841b44f5bd7bd843b1adc"

S = "${WORKDIR}/git/iceoryx2-cxx"

INSANE_SKIP:${PN} += "already-stripped"
FILES_SOLIBSDEV = ""

EXTRA_OECMAKE += "-DCMAKE_INSTALL_PREFIX=${D}/${exec_prefix} -DBUILD_TESTING=ON -DUSE_SYSTEM_GTEST=ON"

PACKAGES =+ "${PN}-tests"
FILES:${PN}-staticdev += "${libdir}/libiceoryx2_cxx.a"
FILES:${PN} += "${libdir}/libiceoryx2_cxx.so"
RDEPENDS:${PN}-dev += "${PN}-staticdev iceoryx-hoofs-subset-dev"
RDEPENDS:${PN} += "iceoryx-hoofs-subset iceoryx2-c"
BBCLASSEXTEND = "native nativesdk"

do_install() {
  cmake --install ${S}/../../build

  install -d ${D}${bindir}/iceoryx2/tests/cxx
  install -m 0755 ${B}/tests/iceoryx2-cxx-tests ${D}${bindir}/iceoryx2/tests/cxx
}

SUMMARY:${PN}-tests = "The iceoryx2 Rust tests"
DESCRIPTION:${PN}-tests = "This package contains the iceoryx2 Rust tests. \
                           They are available in '/usr/bin/iceoryx2/tests'"
HOMEPAGE:${PN}-tests = "https://iceoryx.io"
BUGTRACKER:${PN}-tests = "https://github.com/eclipse-iceoryx/iceoryx2/issues"
FILES:${PN}-tests += "${bindir}/iceoryx2/tests/cxx/*"
