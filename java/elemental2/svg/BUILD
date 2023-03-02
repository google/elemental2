# This package contains the build rule to build elemental2-svg.

load("@com_google_jsinterop_generator//:jsinterop_generator.bzl", "jsinterop_generator")

package(
    default_applicable_licenses = ["//:license"],
    default_visibility = [
        "//:__subpackages__",
    ],
    # Apache2
    licenses = ["notice"],
)

filegroup(
    name = "externs",
    srcs = ["//third_party:svg.js"],
)

# SVG api
jsinterop_generator(
    name = "svg",
    srcs = [":externs"],
    custom_preprocessing_pass = [
        "elemental2.dom.EventListenerCleaner",
    ],
    integer_entities_files = ["integer_entities.txt"],
    # override auto generated js_deps since modified externs cause conflicts
    j2cl_js_deps = [],
    runtime_deps = [
        "//java/elemental2/dom:EventListenerCleaner",
    ],
    deps = [
        "//java/elemental2/core",
        "//java/elemental2/dom",
    ],
)
