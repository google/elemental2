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

lib_version=$1

bazel_root="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

deploy_target='@com_google_j2cl//:deploy'
license_header="license.txt"
group_id="com.google.elemental2"

echo "enter your gpg passphrase:"
read -s gpg_passphrase

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
  bazel run ${deploy_target} -- ${maven_artifact} \
      ${artifact_path}/${jar_file} \
      ${artifact_path}/${src_jar} \
      ${license_header} \
      ${pom_template} \
      ${lib_version} \
      ${group_id} \
      ${gpg_passphrase}
done
