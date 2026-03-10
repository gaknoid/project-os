SUMMARY = "Project base packagegroup"
DESCRIPTION = "Base package set for project images"

inherit packagegroup

# This packagegroup contains many runtime package names that are dynamically
# renamed by the packaging backend (for example, SONAME-based lib package
# outputs). Do not treat it as allarch.
PACKAGE_ARCH = "${TUNE_PKGARCH}"

PACKAGEGROUP_CONTAINER = " \
    ${PACKAGEGROUP_CONTAINER_DOCKER} \
    ${PACKAGEGROUP_CONTAINER_LXC} \
    ${PACKAGEGROUP_CONTAINER_OCI} \
"
PACKAGEGROUP_CONTAINER_DOCKER = " \
    docker \
"
PACKAGEGROUP_CONTAINER_LXC = " \
    lxc \
"
PACKAGEGROUP_CONTAINER_OCI = " \
    virtual-runc \
    oci-systemd-hook \
    oci-runtime-tools \
    oci-image-tools \
"
PACKAGEGROUP_GNOME_APPS = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'polkit', 'gnome-system-monitor gnome-disk-utility', '', d)} \
    evolution-data-server \
    evince \
    file-roller \
    geary \
    gedit \
    ghex \
    gnome-calculator \
    gnome-calendar \
    gnome-commander \
    gnome-font-viewer \
    gnome-photos \
    gnome-screenshot \
    gnome-terminal \
    gnome-text-editor \
    gthumb \
    libwnck3 \
    nautilus \
"
PACKAGEGROUP_GNOME_DESKTOP = " \
    adwaita-icon-theme \
    adwaita-icon-theme-cursors \
    evolution-data-server \
    gmime \
    gnome-backgrounds \
    gnome-bluetooth \
    gnome-control-center \
    gnome-desktop \
    gnome-flashback \
    gnome-keyring \
    gnome-menus \
    gnome-session \
    gnome-settings-daemon \
    gnome-shell \
    gnome-shell-extensions \
    gnome-tweaks \
    gvfs \
    gvfsd-ftp \
    gvfsd-sftp \
    gvfsd-trash \
"
PACKAGEGROUP_META_NETWORKING_CONNECTIVITY = " \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'meta-python', 'firewalld', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth x11', 'blueman', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'samba', '', d)} \
    adcli \
    autossh \
    bearssl \
    cannelloni \
    civetweb \
    daq \
    dhcp-relay \
    dibbler-client \
    dibbler-relay \
    dibbler-server \
    ez-ipupdate \
    freeradius \
    lftp \
    libdnet \
    mbedtls \
    miniupnpd \
    mosquitto \
    mosquitto-clients \
    nanomsg \
    networkmanager \
    networkmanager-openvpn \
    nng \
    openconnect \
    python3-networkmanager \
    rdate \
    rdist \
    relayd \
    sethdlc \
    snort \
    ufw \
    vlan \
    vpnc \
    wolfssl \
"
PACKAGEGROUP_META_NETWORKING_DAEMONS = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'networkd-dispatcher', '', d)} \
    atftp \
    autofs \
    cyrus-sasl \
    igmpproxy \
    ippool \
    iscsi-initiator-utils \
    keepalived \
    lldpd \
    ncftp \
    openhpi \
    opensaf \
    postfix \
    proftpd \
    ptpd \
    pure-ftpd \
    radvd \
    squid \
    tftp-hpa \
    tftp-hpa-server \
    vblade \
    vsftpd \
"
PACKAGEGROUP_META_NETWORKING_EXTENDED = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'dlm', '', d)} \
    corosync \
    kronosnet \
"
PACKAGEGROUP_META_NETWORKING_FILTER = " \
    arno-iptables-firewall \
    conntrack-tools \
    ebtables \
    ipset \
    libnetfilter-acct \
    libnetfilter-conntrack \
    libnetfilter-cthelper \
    libnetfilter-cttimeout \
    libnetfilter-log \
    libnetfilter-queue \
    libnfnetlink \
    libnftnl \
    nfacct \
    nftables \
"
PACKAGEGROUP_META_NETWORKING_KERNEL = " \
    wireguard-tools \
"
PACKAGEGROUP_META_NETWORKING_PROTOCOLS = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'dante', '', d)} \
    babeld \
    freediameter \
    frr \
    net-snmp \
    nopoll \
    openflow \
    openl2tp \
    openlldp \
    pptp-linux \
    radiusclient-ng \
    rp-pppoe \
    tsocks \
    usrsctp \
    xl2tpd \
    zeroconf \
"
PACKAGEGROUP_META_NETWORKING_SUPPORT = " \
    ${@bb.utils.contains_any('TRANSLATED_TARGET_ARCH', 'i586 x86-64', 'spice-protocol spice', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'libldb', '', d)} \
    ${@bb.utils.contains('LICENSE_FLAGS_ACCEPTED', 'non-commercial', 'netperf', '', d)} \
    aoetools \
    arptables \
    bmon \
    bridge-utils \
    celt051 \
    cifs-utils \
    cim-schema-docs \
    cim-schema-final \
    curlpp \
    dnsmasq \
    dovecot \
    drbd-utils \
    esmtp \
    ettercap \
    fetchmail \
    fping \
    fwknop \
    geoip \
    geoip-perl \
    geoipupdate \
    htpdate \
    http-parser \
    ifenslave \
    ifmetric \
    iftop \
    ipcalc \
    ipvsadm \
    libcpr \
    libesmtp \
    libmaxminddb \
    libmemcached \
    libowfat \
    libtalloc \
    libtdb \
    libtevent \
    linux-atm \
    lksctp-tools \
    macchanger \
    mctp \
    memcached \
    mtr \
    nbd-client \
    nbd-server \
    nbd-trdump \
    nbd-trplay \
    nbdkit \
    ncp \
    ndisc6 \
    ndpi \
    netcat \
    netcat-openbsd \
    netcf \
    netsniff-ng \
    ntopng \
    ntp sntp ntpdc ntpq ntp-tickadj ntp-utils \
    nuttcp \
    open-isns \
    openipmi \
    openvpn \
    phytool \
    pimd \
    rdma-core \
    ruli \
    smcroute \
    ssmping \
    ssmtp \
    strongswan \
    stunnel \
    tcpdump \
    tcpreplay \
    tcpslice \
    tinyproxy \
    tnftp \
    traceroute \
    tunctl \
    uftp \
    unbound \
    usbredir \
    vnstat \
    wireshark \
    wpan-tools \
    yp-tools \
    ypbind-mt \
"
PACKAGEGROUP_PODMAN = " \
    podman \
"
X86_TPM_MODULES = ""
X86_TPM_MODULES:x86 = " \
    kernel-module-tpm-atmel \
    kernel-module-tpm-infineon \
    kernel-module-tpm-nsc \
"
X86_TPM_MODULES:x86-64 = " \
    kernel-module-tpm-atmel \
    kernel-module-tpm-infineon \
    kernel-module-tpm-nsc \
"
PACKAGEGROUP_SECURITY_TPM = " \
    ${X86_TPM_MODULES} \
    libhoth \
    openssl-tpm-engine \
    pcr-extend \
    swtpm \
    tpm-quote-tools \
    tpm-tools \
    trousers \
"
PACKAGEGROUP_SECURITY_TPM2 = " \
    libtss2 \
    libtss2-mu \
    libtss2-tcti-device \
    libtss2-tcti-mssim \
    python3-tpm2-pytss \
    tpm2-abrmd \
    tpm2-openssl \
    tpm2-pkcs11 \
    tpm2-tools \
    tpm2-tss \
    tpm2-tss-engine \
    tpm2-tss-engine-engines \
    trousers \
"

PACKAGEGROUP_META_NETWORKING_DAEMONS:remove:libc-musl = "opensaf"
PACKAGEGROUP_META_NETWORKING_CONNECTIVITY:remove:libc-musl = "rdist"

RDEPENDS:${PN} = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '${PACKAGEGROUP_META_NETWORKING_DAEMONS} ${PACKAGEGROUP_META_NETWORKING_EXTENDED}', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', '${PACKAGEGROUP_CONTAINER} ${PACKAGEGROUP_PODMAN}', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '${PACKAGEGROUP_GNOME_APPS} ${PACKAGEGROUP_GNOME_DESKTOP}', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'tpm', '${PACKAGEGROUP_SECURITY_TPM}', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'tpm2', '${PACKAGEGROUP_SECURITY_TPM2}', '', d)} \
    ${PACKAGEGROUP_META_NETWORKING_CONNECTIVITY} \
    ${PACKAGEGROUP_META_NETWORKING_FILTER} \
    ${PACKAGEGROUP_META_NETWORKING_KERNEL} \
    ${PACKAGEGROUP_META_NETWORKING_PROTOCOLS} \
    ${PACKAGEGROUP_META_NETWORKING_SUPPORT} \
    packagegroup-core-ssh-openssh \
    packagegroup-project-wsl2 \
    tayga \
"

EXCLUDE_FROM_WORLD = "1"
