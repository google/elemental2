# Description:
#   Elemental Sample app playing with Promise

load("@com_google_j2cl//build_defs:rules.bzl", "j2cl_application", "j2cl_library")

package(
    default_applicable_licenses = ["//:license"],
    # Apache2
    licenses = ["notice"],
)

j2cl_library(
    name = "promise",
    srcs = glob([
        "*.java",
        "*.js",
    ]),
    deps = [
        "//:elemental2-core-j2cl",
        "//:elemental2-dom-j2cl",
        "//:elemental2-promise-j2cl",
        "@com_google_j2cl//:jsinterop-annotations-j2cl",
        "@com_google_jsinterop_base//:jsinterop-base-j2cl",
    ],
)

j2cl_application(
    name = "promise_app",
    entry_points = ["elemental2.samples.app"],
    deps = [":promise"],
)
