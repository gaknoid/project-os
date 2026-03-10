SUMMARY = "initramfs-framework module for OSTree prepare-root"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS:${PN} = "initramfs-framework-base initramfs-module-rootfs ostree-prepare-root"

inherit allarch

FILESEXTRAPATHS:prepend := "${THISDIR}/initramfs-framework:"
SRC_URI = "file://ostree"

do_install() {
    install -d ${D}/init.d
    install -m 0755 ${WORKDIR}/ostree ${D}/init.d/91-ostree
}

FILES:${PN} = "/init.d/91-ostree"
