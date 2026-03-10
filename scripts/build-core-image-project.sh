#!/usr/bin/env bash
set -Eeuo pipefail
trap 'rc=$?; echo "Error at line $LINENO: \"$BASH_COMMAND\""; exit $rc' ERR

targets=(
  "core-image-minimal"
  "core-image-full-cmdline"
  # "core-image-project"
  # "core-image-project-sdk"
)

templates=(
  "machine-qemux86-64"  
  "machine-intel-corei7-64"
  "machine-jetson-orin-nano-devkit-nvme"
  "machine-qemuarm64"
  "machine-raspberrypi5"
)

for template in ${templates[@]}
do

  pushd .

  rm -rf build/conf build/*.lock build/*.sock build/*.log 

  set +u # disable unbound variable errors
  TEMPLATECONF=../../layers/meta-project/conf/templates/$template \
    source layers/poky/oe-init-build-env build
  set -u # enable unbound variable errors

  bitbake ${targets[@]}

  popd

done
