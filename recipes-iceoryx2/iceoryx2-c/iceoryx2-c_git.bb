SUMMARY = "iceoryx2 C Bindings"
LICENSE = "Apache-2.0 | MIT"
LIC_FILES_CHKSUM = "file://../../LICENSE-APACHE;md5=22a53954e4e0ec258dfce4391e905dac \
                    file://../../LICENSE-MIT;md5=b377b220f43d747efdec40d69fcaa69d"

inherit cmake

DEPENDS = " iceoryx-hoofs iceoryx2"

SRC_URI = "git://github.com/eclipse-iceoryx/iceoryx2.git;protocol=https;branch=main \
           file://0001-Make-C-bindings-build-separately.patch"
SRCREV = "7d2eaebb6c1baad7d50b3e7b13a90b412841a92b"

S = "${WORKDIR}/git/iceoryx2-ffi/c"

INSANE_SKIP:${PN} += " already-stripped"
FILES_SOLIBSDEV = ""

# TODO rename iceoryx2-ffi-artifacts when als the examples and tests are placed there
IOX2_STAGING_DIR = "${STAGING_DIR}/iceoryx2-ffi-artifacts"
EXTRA_OECMAKE += " -DRUST_BUILD_ARTIFACT_PATH=${IOX2_STAGING_DIR}"
EXTRA_OECMAKE += " -DCMAKE_INSTALL_PREFIX=${D}/${exec_prefix}"

FILES_${PN}-staticdev += "${libdir}/libiceoryx2_ffi.a"
FILES:${PN} += "${libdir}/libiceoryx2_ffi.so"
RDEPENDS_${PN}-dev += "${PN}-staticdev"
BBCLASSEXTEND = "native nativesdk"

do_install() {
  cmake --install ${S}/../../../build
}
