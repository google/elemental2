#!/bin/bash -i
#
# Build Elemental's jar files with Bazel.
#
# The script can take one argument that represents the path to the directory
# where the resulting jar files will be copied. If no argument is provided,
# the script will use a temporary directory.

set -e

if [[ "$1" == "" ]]; then
  echo "Error: Publish directory parameter not specified."  >&2
  exit 1
fi

if [[ "$2" == "" ]]; then
  echo "Error: Version parameter not specified."  >&2
  exit 1
fi

artifact_directory=`cd "$1" && pwd`
if [[ "$?" != "0" ]]; then
  echo "Error: ${artifact_directory} is not a directory or could not be accessed."  >&2
  exit 1
fi

version=$2

echo "Building version ${version} and outputting artifacts to ${artifact_directory}"

bazel_root="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
declare -a elemental_artifacts=(core dom indexeddb media promise svg webgl webstorage)

for artifact in "${elemental_artifacts[@]}"; do
  artifact_path="${bazel_root}/bazel-bin/java/elemental2/${artifact}"
  artifact_bazel_path="//java/elemental2/${artifact}"
  jar_file="lib${artifact}.jar"
  src_jar="lib${artifact}-src.jar"

  # ask bazel to explicitly build both jar files
  cd "${bazel_root}"
  bazel build "${artifact_bazel_path}:${jar_file}"
  bazel build "${artifact_bazel_path}:${src_jar}"

  tmp_directory="$(mktemp -d)"
  cd "${tmp_directory}"

  # extract src and class files in order to merge them in one jar.
  jar xf "${artifact_path}/${src_jar}"
  jar xf "${artifact_path}/${jar_file}"

  jar cf "elemental2-${artifact}.jar" .

  mkdir -p "${artifact_directory}/com/google/elemental2/elemental2-${artifact}/${version}"

  cp -f "${bazel_root}/java/elemental2/${artifact}/pom.xml" "${artifact_directory}/com/google/elemental2/elemental2-${artifact}/${version}/elemental2-${artifact}-${version}.pom"
  sed -i '' -e "s/ELEMENTAL2_VERSION/${version}/g" "${artifact_directory}/com/google/elemental2/elemental2-${artifact}/${version}/elemental2-${artifact}-${version}.pom"
  echo "elemental2-${artifact}-${version}.pom created in ${artifact_directory}"

  mv -f "elemental2-${artifact}.jar" "${artifact_directory}/com/google/elemental2/elemental2-${artifact}/${version}/elemental2-${artifact}-${version}.jar"
  echo "elemental2-${artifact}-${version}.jar created in ${artifact_directory}"

  mv -f "${artifact_path}/${src_jar}" "${artifact_directory}/com/google/elemental2/elemental2-${artifact}/${version}/elemental2-${artifact}-${version}-sources.jar"
  echo "elemental2-${artifact}-${version}-sources.jar created in ${artifact_directory}"

  rm -rf "${tmp_directory}"
done
