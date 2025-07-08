#!/bin/bash
# Copyright 2019 Google Inc. All Rights Reserved
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS-IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

#
# The script generates the open source artifacts for elemental and
# uploads them to sonatype.
#
set -euo pipefail

source "$(dirname "$0")/deploy.sh"

readonly GROUP_ID="com.google.elemental2"
readonly ELEMENTAL_ARTIFACTS="core dom indexeddb media promise svg webgl webstorage webassembly"
readonly LICENSE_HEADER="${BAZEL_ROOT}/maven/license.txt"

usage() {
    echo ""
    echo "$(basename $0): Build and deploy script for Elemental2."
    echo ""
    echo "$(basename $0) --version <version> [--no-deploy]"
    echo "    --help"
    echo "        Print this help output and exit."
    echo "    --version <version>"
    echo "        Maven version of the library to use for deploying to sonatype."
    echo "    --no-deploy"
    echo "        Skip the deployment part but build all artifacts."
    echo "    --no-git-tag"
    echo "        Skip the creation of git tag."
    echo ""
}

parse_arguments() {
  deploy_flag=""
  git_tag=true
  lib_version=""

  while [[ $# -gt 0 ]]; do
    case $1 in
      --version )
        shift
        lib_version=$1
        ;;
      --no-deploy )
        deploy_flag="--no-deploy"
        ;;
      --no-git-tag )
        git_tag=false
        ;;
      --help )
        usage
        exit 0
        ;;
      * )
        common::error "unexpected option $1"
        ;;
    esac
    shift
  done
}

check_prerequisites() {
  common::check_bazel
  common::check_maven
  common::check_version_set
}

build() {
  local artifact="$1"
  local artifact_bazel_path="//java/elemental2/${artifact}"
  common::bazel_build "${artifact_bazel_path}:lib${artifact}.jar"
  common::bazel_build "${artifact_bazel_path}:lib${artifact}-src.jar"
  common::bazel_build "${artifact_bazel_path}:${artifact}-javadoc.jar"
}

main() {
  parse_arguments "$@"
  check_prerequisites

  local artifact_dir_flag=""
  if [[ "${deploy_flag}" == "--no-deploy" ]]; then
    # pass a temp directory to the deploy script so that it can create the
    # artifacts in the same directory.
    artifact_dir_flag="--artifact_dir $(mktemp -d)"
  fi

  for artifact in ${ELEMENTAL_ARTIFACTS}; do
    common::info "Building elemental2-${artifact}"
    build "${artifact}"

    local artifact_path="${BAZEL_ROOT}/bazel-bin/java/elemental2/${artifact}"
    local jar_file="${artifact_path}/lib${artifact}.jar"
    local src_jar="${artifact_path}/lib${artifact}-src.jar"
    local javadoc_jar="${artifact_path}/${artifact}-javadoc.jar"
    local pom_template="${BAZEL_ROOT}/maven/pom-${artifact}.xml"
    local maven_artifact="elemental2-${artifact}"

    common::deploy_to_sonatype ${deploy_flag} ${artifact_dir_flag} \
        --artifact "${maven_artifact}" \
        --jar-file  "${jar_file}" \
        --src-jar "${src_jar}" \
        --javadoc-jar "${javadoc_jar}" \
        --license-header "${LICENSE_HEADER}" \
        --pom-template "${pom_template}" \
        --lib-version "${lib_version}" \
        --group-id "${GROUP_ID}"
  done

  if [[ ${git_tag} == true ]]; then
    common::create_and_push_git_tag "${lib_version}"
  fi
}

# Set the trap to cleanup temporary files on EXIT
trap common::cleanup_temp_files EXIT

main "$@"
