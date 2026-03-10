#!/usr/bin/env bash
set -Eeuo pipefail
trap 'rc=$?; echo "Error at line $LINENO: \"$BASH_COMMAND\""; exit $rc' ERR

targets=(
  # "core-image-minimal"
  # "core-image-full-cmdline"
  "core-image-project"
  # "core-image-project-sdk"
)

machines=(
  "intel-corei7-64"  
  "qemux86-64"
  "qemuarm64"
  # "machine-jetson-orin-nano-devkit-nvme"
  # "machine-raspberrypi5"
)

rm -rf build/conf build/*.lock build/*.sock build/*.log build/tmp*/

set +u # disable unbound variable errors
TEMPLATECONF=$(pwd)/.repo/manifests/meta-project/conf/templates/default \
. layers/openembedded-core/oe-init-build-env build
set -u # enable unbound variable errors

export BB_ENV_PASSTHROUGH_ADDITIONS="$BB_ENV_PASSTHROUGH_ADDITIONS MACHINE"

for machine in ${machines[@]}
do

  export MACHINE=$machine

  bitbake ${targets[@]} || exit 1

done
