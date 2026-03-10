FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://podman-override.socket"

inherit useradd
USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "-r docker"

# PACKAGECONFIG:append = "rootless"
PACKAGECONFIG:remove = "docker"

SYSTEMD_AUTO_ENABLE:${PN} = "${@bb.utils.contains('PACKAGECONFIG', 'docker', 'enable', 'disable', d)}"

do_install:append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'docker', 'true', 'false', d)}; then
        install -D -m 0644 ${WORKDIR}/podman-override.socket ${D}${systemd_system_unitdir}/podman.socket.d/override.conf
	fi
    if ${@bb.utils.contains('PACKAGECONFIG', 'rootless', 'true', 'false', d)}; then
        :
	fi
}
