# Description:
#   Elemental Sample app playing with RegExp and dom manipulation

load("@j2cl//build_defs:rules.bzl", "j2cl_application", "j2cl_library")

package(
    default_applicable_licenses = ["//:license"],
    # Apache2
    licenses = ["notice"],
)

j2cl_library(
    name = "regexp",
    srcs = glob([
        "*.java",
        "*.js",
    ]),
    deps = [
        "//:elemental2-core-j2cl",
        "//:elemental2-dom-j2cl",
        "@j2cl//:jsinterop-annotations-j2cl",
        "@jsinterop_base//:jsinterop-base-j2cl",
    ],
)

j2cl_application(
    name = "regexp_app",
    entry_points = ["elemental2.samples.app"],
    deps = [":regexp"],
)
