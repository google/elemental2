# This package contains the build rule to build elemental2-webgl.

package(default_visibility = [
    "//third_party/java/elemental2:__subpackages__",
    "//:__subpackages__",
])

# Apache2
licenses(["notice"])

load("@com_google_jsinterop_generator//:jsinterop_generator.bzl", "jsinterop_generator")

filegroup(
    name = "externs",
    srcs = ["//third_party:webgl.js"],
)

# SVG api
jsinterop_generator(
    name = "webgl",
    srcs = [":externs"],
    extension_type_prefix = "WebGl",
    integer_entities_files = ["integer_entities.txt"],
    deps = [
        "//java/elemental2/core",
        "//java/elemental2/dom",
    ],
    gwt_library_tags = ["maven_coordinates=com.google.elemental2:elemental2-webgl:ELEMENTAL2_VERSION"],
)

load("@google_bazel_common//tools/maven:pom_file.bzl", "pom_file")

pom_file(
    name = "pom.xml",
    targets = [":webgl"],
    template_file = "pom-template.xml",
)
