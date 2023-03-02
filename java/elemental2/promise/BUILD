# This package contains the harcoded source for Promise API.
# This API is hard to generate automaticaly with the JsInterop generator
# and the resulting API is not user friendly

load("@bazel_common_javadoc//:javadoc.bzl", "javadoc_library")
load("@com_google_jsinterop_generator//:jsinterop_generator_import.bzl", "jsinterop_generator_import")

package(
    default_applicable_licenses = ["//:license"],
    default_visibility = [
        "//:__subpackages__",
    ],
    # Apache2
    licenses = ["notice"],
)

jsinterop_generator_import(
    name = "promise",
    srcs = glob(["*.java"]),
    gwt_module_name = "elemental2.promise.Promise",
    gwt_xml = "Promise.gwt.xml",
    types_mapping_files = ["promise.types"],
)

javadoc_library(
    name = "promise-javadoc",
    srcs = glob(["*.java"]),
    tags = [
        "manual",
        "notap",
    ],
    deps = [
        "@com_google_j2cl//:jsinterop-annotations",
        "@com_google_jsinterop_base//:jsinterop-base",
    ],
)
