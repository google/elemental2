# This package contains the build rule to build elemental2-indexeddb.

package(default_visibility = [
    "//third_party/java/elemental2:__subpackages__",
    "//:__subpackages__",
])

# Apache2
licenses(["notice"])

load("@com_google_jsinterop_generator//:jsinterop_generator.bzl", "jsinterop_generator")

filegroup(
    name = "externs",
    srcs = ["//third_party:w3c_indexeddb.js"],
)

jsinterop_generator(
    name = "indexeddb",
    srcs = [":externs"],
    extension_type_prefix = "IndexedDb",
    integer_entities_files = ["integer_entities.txt"],
    deps = [
        "//java/elemental2/core",
        "//java/elemental2/dom",
    ],
)
