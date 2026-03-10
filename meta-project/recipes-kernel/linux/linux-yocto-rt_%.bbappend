FILESEXTRAPATHS:prepend := "${THISDIR}/linux:"

# require ${@bb.utils.contains_any('DISTRO_FEATURES', 'tpm tpm2', 'recipes-kernel/linux/linux-yocto_tpm.inc', '', d)}

SRC_URI:append = " \
    file://nfs-root.cfg \
    file://no-logo.cfg \
"
