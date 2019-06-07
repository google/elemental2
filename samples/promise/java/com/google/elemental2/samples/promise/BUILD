# Description:
#   Elemental Sample app playing with Promise

package(
    # Apache2
    licenses = ["notice"],
)

load("@com_google_j2cl//build_defs:rules.bzl", "j2cl_application", "j2cl_library")

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
        "//third_party:jsinterop-annotations",
        "//third_party:jsinterop-base",
    ],
)

j2cl_application(
    name = "promise_app",
    entry_points = ["elemental2.samples.app"],
    deps = [":promise"],
)
