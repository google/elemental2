# This package contains the build rule to build elemental2-media.

package(default_visibility = [
    "//third_party/java/elemental2:__subpackages__",
    "//:__subpackages__",
])

# Apache2
licenses(["notice"])

load("@com_google_jsinterop_generator//:jsinterop_generator.bzl", "jsinterop_generator")

filegroup(
    name = "externs",
    srcs = [
        "//third_party:mediakeys.js",
        "//third_party:w3c_audio.js",
    ],
)

# SVG api
jsinterop_generator(
    name = "media",
    srcs = [":externs"],
    integer_entities_files = ["integer_entities.txt"],
    deps = [
        "//java/elemental2/core",
        "//java/elemental2/dom",
        "//java/elemental2/promise",
    ],
    gwt_library_tags = ["maven_coordinates=com.google.elemental2:elemental2-media:ELEMENTAL2_VERSION"],
)

load("@google_bazel_common//tools/maven:pom_file.bzl", "pom_file")

pom_file(
    name = "pom.xml",
    targets = [":media"],
    template_file = "pom-template.xml",
)
