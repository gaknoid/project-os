#!/bin/bash
set -eo pipefail

WORKSPACE="${WORKSPACE:-${GITHUB_WORKSPACE}}"

cd "$WORKSPACE"

TEMPLATECONF="$WORKSPACE/layers/meta-project/conf/templates/${BITBAKE_TEMPLATE}" \
  . layers/poky/oe-init-build-env build >/dev/null

mkdir -p "$WORKSPACE/build/release-logs"
bitbake "$BITBAKE_TARGET" 2>&1 | tee "$WORKSPACE/build/release-logs/bitbake-${BITBAKE_TARGET}.log"
