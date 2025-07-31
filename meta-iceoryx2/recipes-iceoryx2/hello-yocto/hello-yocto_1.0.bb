SUMMARY = "Simple Yocto hello-world example in Rust"
LICENSE = "Apache-2.0 | MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit cargo_bin

# Enaable network for the compile task allowing cargo to download dependencies
do_compile[network] = "1"

SRC_URI = " \
    file://Cargo.toml \
    file://src/main.rs \
"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/${TARGET_ARCH}-unknown-linux-gnu/release/hello-yocto ${D}${bindir}/
}

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/usr/bin
    install -m 0755 ${S}/hello-yocto ${DEPLOY_DIR_IMAGE}/usr/bin/
}
