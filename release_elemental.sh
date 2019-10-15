#!/bin/bash -i
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
# upload them to sonatype.
#
set -e

usage() {
    echo ""
    echo "$(basename $0): Build and deploy script for Elemental2."
    echo ""
    echo "$(basename $0) --version <version> [--no-deploy]"
    echo "    --help"
    echo "        Print this help output and exit."
    echo "    --version-file <version>"
    echo "        Maven version to use for deploying to sonatype."
    echo "    --no-deploy"
    echo "        Skip the deployment part but build all artifacts."
    echo ""
}

deploy=true

while [[ "$1" != "" ]]; do
  case $1 in
    --version )    if [ -z $2 ] || [[ $2 == "--no-deploy" ]]; then
                     echo "Error: Incorrect version value."
                     usage
                     exit 1
                   fi
                   shift
                   lib_version=$1
                   ;;
    --no-deploy )  deploy=false
                   ;;
    --help )       usage
                   exit 1
                   ;;
    * )            echo "Error: unexpected option $1"
                   usage
                   exit 1
                   ;;
  esac
  shift
done

if [[ -z "$lib_version" ]]; then
  echo "Error: --version flag is missing"
  usage
  exit 1
fi

bazel_root="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

deploy_target='@com_google_j2cl//:deploy'
license_header="${bazel_root}/license.txt"
group_id="com.google.elemental2"
gpg_flag=""
deploy_flag=""
artifact_directory_flag=""

if [[ ${deploy} == true ]]; then
  echo "enter your gpg passphrase:"
  read -s gpg_passphrase
  if [[ -n "$gpg_passphrase" ]]; then
    gpg_flag="--gpg-passphrase ${gpg_passphrase}"
  fi
else
  deploy_flag="--no-deploy"
  artifact_directory_flag="--artifact_dir $(mktemp -d)"
fi

cd ${bazel_root}

elemental_artifacts="core dom indexeddb media promise svg webgl webstorage webassembly"

for artifact in ${elemental_artifacts}; do
  artifact_path=${bazel_root}/bazel-bin/java/elemental2/${artifact}
  artifact_bazel_path=//java/elemental2/${artifact}
  jar_file=lib${artifact}.jar
  src_jar=lib${artifact}-src.jar

  # ask bazel to explicitly build both jar files
  bazel build ${artifact_bazel_path}:${jar_file}
  bazel build ${artifact_bazel_path}:${src_jar}

  pom_template=${bazel_root}/maven/pom-${artifact}.xml
  maven_artifact="elemental2-${artifact}"

  # run that script with bazel as its a dependency of this project
  bazel run ${deploy_target} --  ${deploy_flag} ${gpg_flag} ${artifact_directory_flag} \
    --artifact ${maven_artifact} \
    --jar-file  ${artifact_path}/${jar_file} \
    --src-jar ${artifact_path}/${src_jar} \
    --license-header ${license_header} \
    --pom-template ${pom_template} \
    --lib-version ${lib_version} \
    --group-id ${group_id}

done
