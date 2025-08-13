SUMMARY = "iceoryx2"
LICENSE = "Apache-2.0 | MIT"
LIC_FILES_CHKSUM = "file://LICENSE-APACHE;md5=22a53954e4e0ec258dfce4391e905dac \
                    file://LICENSE-MIT;md5=b377b220f43d747efdec40d69fcaa69d"

# Enable network for the compile task allowing cargo to download dependencies
do_compile[network] = "1"

# zlib and expath due to python ... maybe just add python ... should be build dependency
DEPENDS = " zlib expat python3"

SRC_URI = "git://github.com/eclipse-iceoryx/iceoryx2.git;protocol=https;branch=main"
SRCREV = "7d2eaebb6c1baad7d50b3e7b13a90b412841a92b"

S = "${WORKDIR}/git"

inherit cargo_bin

INSANE_SKIP:${PN} += " already-stripped"
FILES_SOLIBSDEV = ""

# This should actually be handled by meta-rust-bin most likely
# in classes/cargo_bin.bbclass similar to Yocto Poky
# meta/classes-recipe/rust-common.bbclass populating RUSTFLAGS
# to prevent bitbake warnings "contains reference to TMPDIR [buildpaths]"
# At this time, meta-rust-bin allows to pass in additional flags
# via EXTRA_RUSTFLAGS to populate RUSTFLAGS
RUST_DEBUG_REMAP = "--remap-path-prefix=${WORKDIR}=${TARGET_DBGSRC_DIR}"
# See https://github.com/rust-embedded/meta-rust-bin/blob/master/classes/cargo_bin.bbclass
# for variables to control the compilations
EXTRA_RUSTFLAGS = "${RUST_DEBUG_REMAP}"
CARGO_FEATURES = "libc_platform"
EXTRA_CARGO_FLAGS = " --tests --workspace --all-targets --exclude iceoryx2-ffi-python"

BBCLASSEXTEND = "native nativesdk"

PACKAGES =+ "${PN}-cli ${PN}-benchmarks ${PN}-examples ${PN}-tests"

IOX2_STAGING_DIR = "${STAGING_DIR}/iceoryx2-artifacts"

do_install() {
    install -d ${IOX2_STAGING_DIR}
    install ${CARGO_BINDIR}/libiceoryx2_ffi.a ${IOX2_STAGING_DIR}
    install -m 0755 ${CARGO_BINDIR}/libiceoryx2_ffi.so ${IOX2_STAGING_DIR}

    install -d ${IOX2_STAGING_DIR}/iceoryx2-ffi-cbindgen/include/iox2
    install ${CARGO_BINDIR}/iceoryx2-ffi-cbindgen/include/iox2/iceoryx2.h ${IOX2_STAGING_DIR}/iceoryx2-ffi-cbindgen/include/iox2

    install -d ${D}${bindir}
    install -m 0755 ${CARGO_BINDIR}/iox2 ${D}${bindir}
    install -m 0755 ${CARGO_BINDIR}/iox2-config ${D}${bindir}
    install -m 0755 ${CARGO_BINDIR}/iox2-node ${D}${bindir}
    install -m 0755 ${CARGO_BINDIR}/iox2-service ${D}${bindir}
    install -m 0755 ${CARGO_BINDIR}/iox2-tunnel ${D}${bindir}

    install -d ${D}${bindir}/iceoryx2/benchmarks
    install -m 0755 ${CARGO_BINDIR}/benchmark-event ${D}${bindir}/iceoryx2/benchmarks
    install -m 0755 ${CARGO_BINDIR}/benchmark-publish-subscribe ${D}${bindir}/iceoryx2/benchmarks
    install -m 0755 ${CARGO_BINDIR}/benchmark-queue ${D}${bindir}/iceoryx2/benchmarks
    install -m 0755 ${CARGO_BINDIR}/benchmark-request-response ${D}${bindir}/iceoryx2/benchmarks

    install -d ${D}${bindir}/iceoryx2/examples
    for example in ${CARGO_BINDIR}/examples/*; do
        if [ -f "$example" ] && [ -x "$example" ] && [ "${example##*.}" != "so" ]; then
            install -m 0755 "$example" ${D}${bindir}/iceoryx2/examples
        fi
    done

    install -d ${D}${bindir}/iceoryx2/tests
    for test in ${CARGO_BINDIR}/deps/*; do
        if [ -f "$test" ] && [ -x "$test" ] && [ "${test##*.}" != "so" ]; then
            install -m 0755 "$test" ${D}${bindir}/iceoryx2/tests
        fi
    done
}

SUMMARY:${PN}-cli = "Package for iceoryx2-cli"
FILES:${PN}-cli += "${bindir}/iox2"
FILES:${PN}-cli += "${bindir}/iox2-config"
FILES:${PN}-cli += "${bindir}/iox2-node"
FILES:${PN}-cli += "${bindir}/iox2-service"
FILES:${PN}-cli += "${bindir}/iox2-tunnel"

SUMMARY:${PN}-benchmarks = "Package for iceoryx2-benchmarks"
FILES:${PN}-benchmarks += "${bindir}/iceoryx2/benchmarks/*"

SUMMARY:${PN}-examples = "Package for iceoryx2-examples"
FILES:${PN}-examples += "${bindir}/iceoryx2/examples/*"

SUMMARY:${PN}-tests = "Package for iceoryx2-tests"
FILES:${PN}-tests += "${bindir}/iceoryx2/tests/*"
