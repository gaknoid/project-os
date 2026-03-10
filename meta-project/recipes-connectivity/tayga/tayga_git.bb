SUMMARY = "Stateless NAT64 for Linux"
DESCRIPTION = "TAYGA is an out-of-kernel stateless NAT64 implementation for Linux. \
It uses the TUN driver to exchange packets with the kernel."
HOMEPAGE = "https://github.com/apalrd/tayga"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/apalrd/tayga;branch=main;protocol=https"
SRCREV = "7f00122b75d808f696df0efe481a8c8919ba9bd7"
PV = "0.9.6+git${SRCPV}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "\
    prefix=${prefix} \
    sbindir=${sbindir} \
    mandir=${mandir} \
    servicedir=${systemd_system_unitdir} \
    TAYGA_VERSION='${PV}' \
    TAYGA_BRANCH='0.9.6' \
    TAYGA_COMMIT='7f00122b75d808f696df0efe481a8c8919ba9bd7' \
    WITH_SYSTEMD='1' \
"

do_install() {
    oe_runmake install DESTDIR=${D}
}

# Check for kernel config, QA check issues false positive
# RDEPENDS:${PN} += "kernel-module-tun"

FILES:${PN} += "${sbindir}/ ${systemd_system_unitdir}/"
FILES:${PN}-doc += "${mandir}/man5/tayga.conf.5 ${mandir}/man8/tayga.8"
