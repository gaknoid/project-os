# OSTree commit ref
OSTREE_BRANCHNAME = "${MACHINE}/${BPN}"
# OSTree REPO options
OSTREE_REPO_CONFIG:append = " composefs.enable:true"
OSTREE_OTA_REPO_CONFIG:append = " composefs.enable:true"

# poky remove connman for network configuration
IMAGE_INSTALL:remove = " \
	connman \
	connman-plugin-ethernet \
"

# sota supplies OTA clients by default; strip them from project images.
IMAGE_INSTALL:remove:sota = " \
	aktualizr \
	aktualizr-info \
	${SOTA_CLIENT_PROV} \
	aktualizr-shared-prov \
	aktualizr-shared-prov-creds \
	aktualizr-device-prov \
	aktualizr-device-prov-hsm \
	aktualizr-resource-control \
	aktualizr-secondary \
	aktualizr-secondary-lib \
	aktualizr-lib \
	aktualizr-configs \
	aktualizr-sotatools-lib \
	garage-sign \
	garage-push \
	garage-check \
"

ROOTFS_POSTUNINSTALL_COMMAND += "rootfs_usr_share_factory;"

# Build UKI for EFI machines as an image build dependency, not a runtime package.
do_image[depends] += "${@bb.utils.contains('MACHINE_FEATURES', 'efi', 'linux-uki-native:do_deploy', '', d)}"

rootfs_usr_share_factory () {
	mkdir -p ${IMAGE_ROOTFS}/usr/share/factory/
	cp -a ${IMAGE_ROOTFS}/boot/ ${IMAGE_ROOTFS}/usr/share/factory/boot/
	cp -a ${IMAGE_ROOTFS}/var/ ${IMAGE_ROOTFS}/usr/share/factory/var/
}
