"""Bazel rule for loading external repository deps for elemental2."""

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

_JSINTEROP_GENERATOR_VERSION = "master"
_CLOSURE_COMPILER_VERSION = "master"

def load_elemental2_repo_deps():
    http_archive(
        name = "com_google_closure_compiler",
        url = "https://github.com/google/closure-compiler/archive/%s.zip" % _CLOSURE_COMPILER_VERSION,
        build_file = Label("//:jscomp.BUILD"),
        strip_prefix = "closure-compiler-%s" % _CLOSURE_COMPILER_VERSION,
    )

    http_archive(
        name = "com_google_jsinterop_generator",
        url = "https://github.com/google/jsinterop-generator/archive/%s.zip" % _JSINTEROP_GENERATOR_VERSION,
        strip_prefix = "jsinterop-generator-%s" % _JSINTEROP_GENERATOR_VERSION,
    )
