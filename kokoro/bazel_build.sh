#!/bin/bash
set -e

chmod +x ${KOKORO_GFILE_DIR}/setup_build_environment.sh
${KOKORO_GFILE_DIR}/setup_build_environment.sh

# the repo is cloned under git/elemental2
cd git/elemental2

bazel build ... --incompatible_remove_native_http_archive=false
