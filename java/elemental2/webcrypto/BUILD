# This package contains the build rule to build elemental2-dom.

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
    srcs = [
        "//third_party:w3c_webcrypto.js",
    ],
)

jsinterop_generator(
    name = "webcrypto",
    srcs = [":externs"],
    # override auto generated js_deps since modified externs cause conflicts
    externs_deps = [],
    deps = [
        "//java/elemental2/core",
        "//java/elemental2/promise",
    ],
)
