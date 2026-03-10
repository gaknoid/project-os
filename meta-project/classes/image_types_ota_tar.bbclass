OTA_CMD_TAR = "tar ${@bb.utils.contains('DISTRO_FEATURES', 'selinux', '--selinux', '', d)} --xattrs --xattrs-include=*"
EXTRA_IMAGECMD:ota-tar ?= ""
IMAGE_TYPEDEP:ota-tar = "ota"
IMAGE_ROOTFS:task-image-ota-tar = "${OTA_SYSROOT}"
IMAGE_CMD:ota-tar () {
	${OTA_CMD_TAR} -S -C ${OTA_SYSROOT} -cf ${IMGDEPLOYDIR}/${IMAGE_NAME}.ota-tar .
}
do_image_ota_tar[depends] += ""
