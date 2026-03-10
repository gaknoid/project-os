SUMMARY = "rpm-ostree host and target tools"
DESCRIPTION = "rpm-ostree is a hybrid image/package system built on OSTree and RPM. This recipe builds the upstream git tree for both target and native use."
HOMEPAGE = "https://github.com/coreos/rpm-ostree"
LICENSE = "GPL-2.0-or-later & LGPL-2.1-or-later & (Apache-2.0 | MIT)"
SECTION = "devel"

LIC_FILES_CHKSUM = "file://LICENSE;md5=01a124896c40fcd477634ecc07d7efa1"

S = "${WORKDIR}/git"

SRC_URI = " \
    gitsm://github.com/coreos/rpm-ostree;branch=main;protocol=https;name=rpm_ostree \
    git://github.com/containers/bootc;protocol=https;nobranch=1;name=bootc;destsuffix=bootc;type=git-dependency \
    git://github.com/containers/composefs-rs;protocol=https;nobranch=1;name=composefs_rs;destsuffix=composefs-rs;type=git-dependency \
"

SRCREV_FORMAT = "rpm_ostree_bootc_composefs_rs"
SRCREV_rpm_ostree = "7e2f2065a4aa4d5965b4537bb7d74e0b2898650e"
SRCREV_bootc = "b76d75d6024bd0aa0f270ff181807aff4209f9c9"
SRCREV_composefs_rs = "e9008489375044022e90d26656960725a76f4620"

inherit autotools bash-completion cargo_common cargo-update-recipe-crates gettext pkgconfig rust-target-config

BASEDEPENDS:append = " cargo-native"

DEPENDS = " \
    attr \
    bash-completion \
    cmake \
    curl \
    expat \
    glib-2.0 \
    json-c \
    json-glib \
    libarchive \
    libcap \
    libcheck \
    libmodulemd \
    librepo \
    libsolv \
    libzstd \
    ostree \
    openssl \
    polkit \
    rpm \
    sqlite3 \
    systemd \
    util-linux \
"

require ${BPN}-crates.inc

export RUST_BACKTRACE = "full"
export RUSTFLAGS
export RUST_TARGET = "${RUST_HOST_SYS}"

EXTRA_OECONF = " \
    --disable-bin-unit-tests \
    --disable-gtk-doc \
    --disable-introspection \
    --disable-silent-rules \
    --disable-werror \
"

do_configure() {
	NOCONFIGURE=1 ./autogen.sh
	cargo_common_do_configure
	oe_runconf
}

BBCLASSEXTEND = "native"
