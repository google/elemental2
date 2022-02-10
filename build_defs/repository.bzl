"""Bazel rule for loading external repository deps for elemental2."""

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

_JSINTEROP_GENERATOR_VERSION = "master"
_CLOSURE_COMPILER_VERSION = "f4e77b5d65027720e85e619d7b46dc37258e0911"

def load_elemental2_repo_deps():
    http_archive(
        name = "com_google_closure_compiler",
        url = "https://github.com/google/closure-compiler/archive/%s.zip" % _CLOSURE_COMPILER_VERSION,
        build_file = Label("//build_defs/internal_do_not_use:jscomp.BUILD"),
        strip_prefix = "closure-compiler-%s" % _CLOSURE_COMPILER_VERSION,
    )

    http_archive(
        name = "com_google_jsinterop_generator",
        url = "https://github.com/google/jsinterop-generator/archive/%s.zip" % _JSINTEROP_GENERATOR_VERSION,
        strip_prefix = "jsinterop-generator-%s" % _JSINTEROP_GENERATOR_VERSION,
    )

    http_archive(
        name = "org_gwtproject_gwt",
        strip_prefix = "gwt-073679594c6ead7abe501009f8ba31eb390047fc",
        url = "https://github.com/gwtproject/gwt/archive/073679594c6ead7abe501009f8ba31eb390047fc.zip",
        sha256 = "731879b8e56024a34f36b83655975a474e1ac1dffdfe72724e337976ac0e1749",
    )
