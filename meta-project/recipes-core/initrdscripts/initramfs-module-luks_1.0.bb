SUMMARY = "initramfs-framework module for LUKS rootfs unlocking"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS:${PN} = "initramfs-framework-base cryptsetup util-linux-blkid"

inherit allarch

FILESEXTRAPATHS:prepend := "${THISDIR}/initramfs-framework:"
SRC_URI = "file://luks"

do_install() {
    install -d ${D}/init.d
    install -m 0755 ${WORKDIR}/luks ${D}/init.d/08-luks
}

FILES:${PN} = "/init.d/08-luks"
