workspace(name = "com_google_elemental2")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

maven_server(
    name = "sonatype_snapshot",
    url = "https://oss.sonatype.org/content/repositories/snapshots",
)

http_archive(
    name = "google_bazel_common",
    strip_prefix = "bazel-common-2782531da81d4002bce16e853953d9e8117a6fc1",
    url = "https://github.com/google/bazel-common/archive/2782531da81d4002bce16e853953d9e8117a6fc1.tar.gz",
)

load("@google_bazel_common//:workspace_defs.bzl", "google_common_workspace_rules")

google_common_workspace_rules()

http_archive(
    name = "com_google_closure_compiler",
    build_file = "//:BUILD.jscomp",
    strip_prefix = "closure-compiler-master",
    url = "https://github.com/google/closure-compiler/archive/master.tar.gz",
)

http_archive(
    name = "com_google_jsinterop_generator",
    strip_prefix = "jsinterop-generator-master",
    url = "https://github.com/google/jsinterop-generator/archive/master.zip",
)

load("@com_google_jsinterop_generator//:workspace_defs.bzl", "jsinterop_generator_repositories")

jsinterop_generator_repositories()
