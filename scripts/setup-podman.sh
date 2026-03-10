#!/usr/bin/env bash
set -Eeuo pipefail
trap 'rc=$?; echo "Error at line $LINENO: \"$BASH_COMMAND\"" >&2; exit $rc' ERR

PODMAN_USER="${PODMAN_USER:-builds}"
PODMAN_GROUP="${PODMAN_GROUP:-${PODMAN_USER}}"
PODMAN_UID="${PODMAN_UID:-1001}"
PODMAN_SUBUID_START="${PODMAN_SUBUID_START:-100000}"
PODMAN_SUBGID_START="${PODMAN_SUBGID_START:-100000}"
PODMAN_SUBID_COUNT="${PODMAN_SUBID_COUNT:-65536}"

if [[ ${EUID} -ne 0 ]]; then
  echo "Run this script as root." >&2
  exit 1
fi

export DEBIAN_FRONTEND=noninteractive

apt-get update
apt-get install -y \
  podman \
  podman-docker \
  uidmap \
  slirp4netns \
  fuse-overlayfs \
  dbus-user-session

cat >/etc/sysctl.d/99-podman.conf <<'EOF'
user.max_user_namespaces=28633
EOF
sysctl --system

if ! getent group "${PODMAN_GROUP}" >/dev/null; then
  groupadd --gid "${PODMAN_UID}" "${PODMAN_GROUP}"
fi

if ! id -u "${PODMAN_USER}" >/dev/null 2>&1; then
  useradd \
    --create-home \
    --home-dir "/home/${PODMAN_USER}" \
    --shell /bin/bash \
    --uid "${PODMAN_UID}" \
    --gid "${PODMAN_GROUP}" \
    --comment "Yocto build user" \
    "${PODMAN_USER}"
fi

if ! grep -q "^${PODMAN_USER}:" /etc/subuid 2>/dev/null; then
  printf '%s:%s:%s\n' "${PODMAN_USER}" "${PODMAN_SUBUID_START}" "${PODMAN_SUBID_COUNT}" >> /etc/subuid
fi

if ! grep -q "^${PODMAN_USER}:" /etc/subgid 2>/dev/null; then
  printf '%s:%s:%s\n' "${PODMAN_USER}" "${PODMAN_SUBGID_START}" "${PODMAN_SUBID_COUNT}" >> /etc/subgid
fi

loginctl enable-linger "${PODMAN_USER}"

install -d -o "${PODMAN_USER}" -g "${PODMAN_GROUP}" -m 700 "/run/user/${PODMAN_UID}"

if systemctl --machine="${PODMAN_USER}@.host" --user enable --now podman.socket; then
  systemctl --machine="${PODMAN_USER}@.host" --user status podman.socket --no-pager
else
  sudo -iu "${PODMAN_USER}" env \
    XDG_RUNTIME_DIR="/run/user/${PODMAN_UID}" \
    DBUS_SESSION_BUS_ADDRESS="unix:path=/run/user/${PODMAN_UID}/bus" \
    systemctl --user enable --now podman.socket

  sudo -iu "${PODMAN_USER}" env \
    XDG_RUNTIME_DIR="/run/user/${PODMAN_UID}" \
    DBUS_SESSION_BUS_ADDRESS="unix:path=/run/user/${PODMAN_UID}/bus" \
    systemctl --user status podman.socket --no-pager
fi

cat <<EOF

Podman host setup complete.

Next checks:
  sudo -iu ${PODMAN_USER} podman info
  sudo -iu ${PODMAN_USER} docker --version
EOF
