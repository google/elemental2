#!/bin/bash -i
#
# The script generates the open source artifacts for elemental and
# upload them to sonatype.
#
set -e

lib_version=$1

bazel_root="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

deploy_target='@com_google_jsinterop_base//:deploy'
license_header="$(bazel info output_base)/external/com_google_jsinterop_base/license.txt"
# The group id can be customized in case you want to build using a custom groupid
group_id=${ELEMENTAL2_GROUP_ID:-com.google.elemental2}

# The invoker of script can pass GPG password to avoid having
# the script wait for user to supply credentials interactively
gpg_passphrase=${ELEMENTAL2_GPG_PASS:-}

if [ -z ${gpg_passphrase} ]; then
  echo "enter your gpg passphrase:"
  read -s gpg_passphrase
fi

cd ${bazel_root}

elemental_artifacts="core dom indexeddb media promise svg webgl webstorage"

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
  bazel run ${deploy_target} -- ${maven_artifact} ${artifact_path}/${jar_file} ${artifact_path}/${src_jar} ${license_header} ${pom_template} ${lib_version} ${group_id} ${gpg_passphrase}
done
