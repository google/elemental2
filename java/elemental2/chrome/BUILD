# This package contains the build rule to build elemental2-chrome.

package(default_visibility = [
    "//:__subpackages__",
])

# Apache2
licenses(["notice"])

load(
    "@com_google_jsinterop_generator//:jsinterop_generator.bzl",
    "jsinterop_generator",
)

load(
    "//build_defs/internal_do_not_use:elemental_utils.bzl",
    "patch_extern_file",
)

patch_extern_file(
    name = "chrome_patched",
    src = "//third_party:chrome.js",
    patch_file = "chrome.js.diff",
)

filegroup(
    name = "externs",
    srcs = [":chrome_patched"],
)

jsinterop_generator(
    name = "chrome",
    srcs = [":externs"],
    integer_entities_files = ["integer_entities.txt"],
    name_mapping_files = ["name_mappings.txt"],
    deps = [
        "//java/elemental2/core",
        "//java/elemental2/dom",
    ])
