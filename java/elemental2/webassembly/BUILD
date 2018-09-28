# This package contains the build rule to build elemental2-webassembly.

package(default_visibility = [
    "//:__subpackages__",
])

# Apache2
licenses(["notice"])

load("@com_google_jsinterop_generator//:jsinterop_generator.bzl", "jsinterop_generator")

filegroup(
    name = "externs",
    srcs = ["//third_party:webassembly.js"],
)

jsinterop_generator(
    name = "webassembly",
    srcs = [":externs"],
    extension_type_prefix = "WebAssembly",
    name_mapping_files = ["name_mappings.txt"],
    integer_entities_files = ["integer_entities.txt"],
    deps = [
        "//java/elemental2/core",
        "//java/elemental2/promise",
    ],
)
