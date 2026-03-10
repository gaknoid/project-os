SUMMARY = "initramfs-framework module for TPM device setup and key retrieval"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS:${PN} = "initramfs-framework-base \
    ${@bb.utils.contains_any('MACHINE_FEATURES', 'tpm tpm2', \
        'kernel-module-tpm \
         kernel-module-tpm-tis \
         kernel-module-tpm-tis-core \
         kernel-module-tpm-crb \
         kernel-module-tpm-tis-spi', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'tpm', 'tpm-tools', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'tpm2', 'tpm2-tools', '', d)} \
"

inherit allarch

FILESEXTRAPATHS:prepend := "${THISDIR}/initramfs-framework:"
SRC_URI = "file://tpm"

do_install() {
    install -d ${D}/init.d
    install -m 0755 ${WORKDIR}/tpm ${D}/init.d/07-tpm
}

FILES:${PN} = "/init.d/07-tpm"
