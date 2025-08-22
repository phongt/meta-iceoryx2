SUMMARY = "iceoryx classic platform abstraction layer"
DESCRIPTION = "This package contains the iceoryx classic platform abstraction layer in a minimal version, intended to be used with the iceoryx2 C++ bindings."
HOMEPAGE = "https://iceoryx.io"
BUGTRACKER = "https://github.com/eclipse-iceoryx/iceoryx/issues"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=46d6aa0ba1fa2ed247cd8d42f20b72f4"

inherit cmake

DEPENDS = ""

SRC_URI = "git://git@github.com/eclipse-iceoryx/iceoryx.git;protocol=ssh;branch=main"
SRCREV = "9e82c99738cee9a796e7a7dac47e847f70a4b45d"

S = "${WORKDIR}/git/iceoryx_platform"

EXTRA_OECMAKE = "-DIOX_PLATFORM_MINIMAL_POSIX=ON"

BBCLASSEXTEND = "native nativesdk"
