"""Macro to use for loading the elemental2 repository"""

load("@com_google_jsinterop_generator//build_defs:rules.bzl", "setup_jsinterop_generator_workspace")
load("@com_google_jsinterop_generator//build_defs:repository.bzl", "load_jsinterop_generator_repo_deps")

def setup_elemental2_workspace():
    load_jsinterop_generator_repo_deps()
    setup_jsinterop_generator_workspace()
