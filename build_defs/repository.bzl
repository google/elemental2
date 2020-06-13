"""Bazel rule for loading external repository deps for elemental2."""

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

_JSINTEROP_GENERATOR_VERSION = "master"
_CLOSURE_COMPILER_VERSION = "master"

def load_elemental2_repo_deps(
    closure_compiler_version = _CLOSURE_COMPILER_VERSION,
    jsinterop_generator_version = _JSINTEROP_GENERATOR_VERSION):

    http_archive(
        name = "com_google_closure_compiler",
        url = "https://github.com/google/closure-compiler/archive/%s.zip" % closure_compiler_version,
        build_file = Label("//build_defs/internal_do_not_use:jscomp.BUILD"),
        strip_prefix = "closure-compiler-%s" % closure_compiler_version,
    )

    http_archive(
        name = "com_google_jsinterop_generator",
        url = "https://github.com/google/jsinterop-generator/archive/%s.zip" % jsinterop_generator_version,
        strip_prefix = "jsinterop-generator-%s" % jsinterop_generator_version,
    )
