load("@com_google_j2cl//build_defs:rules.bzl", "j2cl_application", "j2cl_library")

package(
    licenses = ["notice"],  # Apache 2.0
)

j2cl_library(
    name = "helloworld",
    srcs = glob([
        "*.java",
        "*.js",
    ]),
    deps = [
        "//:elemental2-dom-j2cl",
        "@com_google_j2cl//:jsinterop-annotations-j2cl",
    ],
)

j2cl_application(
    name = "helloworld_app",
    entry_points = ["elemental2.samples.app"],
    deps = [":helloworld"],
)
