DESCRIPTION = "Unified Kernel Image build using ukify"
LICENSE = "MIT"
DEPENDS = "virtual/kernel systemd-boot-native python3-pefile-native"

KERNEL_CMDLINE ??= "${APPEND} rw quiet"
KERNEL_IMAGE ??= "${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}"
INITRAMFS_IMAGE ??= "initramfs-ostree-image"
INITRAMFS_IMAGE_NAME ??= "${INITRAMFS_IMAGE}-${MACHINE}"
UKI_STUB ??= "${DEPLOY_DIR_IMAGE}/linux*.efi.stub"

inherit deploy python3native

# systemd-boot supplies linux*.efi.stub
do_compile[depends] += "virtual/kernel:do_deploy ${INITRAMFS_IMAGE}:do_image_complete systemd-boot:do_deploy"

do_compile() {
    kernel_input="$(readlink -f ${KERNEL_IMAGE})"
    kernel_basename="$(basename ${kernel_input})"
    kernel_basename="${kernel_basename%.bin}"
    uki_basename="${kernel_basename}.efi"

    PYTHONPATH="${RECIPE_SYSROOT_NATIVE}${PYTHON_SITEPACKAGES_DIR}:${PYTHONPATH}" \
    ${PYTHON} ${STAGING_BINDIR_NATIVE}/ukify build \
        --stub ${UKI_STUB} \
        --linux ${kernel_input} \
        --initrd ${DEPLOY_DIR_IMAGE}/${INITRAMFS_IMAGE_NAME}.cpio.gz \
        --cmdline "${KERNEL_CMDLINE}" \
        --output ${B}/${uki_basename}

    ln -snf ${uki_basename} ${B}/${KERNEL_IMAGETYPE}.efi
}

# do_install() {
#     install -D -m 0644 ${B}/${KERNEL_IMAGE_NAME}.efi ${D}/boot/${KERNEL_IMAGE_NAME}.efi
# }

do_deploy() {
    kernel_input="$(readlink -f ${KERNEL_IMAGE})"
    kernel_basename="$(basename ${kernel_input})"
    kernel_basename="${kernel_basename%.bin}"
    uki_basename="${kernel_basename}.efi"

    install -d ${DEPLOYDIR}
    install -D -m 0644 ${B}/${uki_basename} ${DEPLOYDIR}/${uki_basename}
    ln -snf ${uki_basename} ${DEPLOYDIR}/${KERNEL_IMAGETYPE}.efi
}
addtask deploy after do_compile

ALLOW_EMPTY:${PN} = "1"

FILES:${PN} += "/boot/*.efi"
