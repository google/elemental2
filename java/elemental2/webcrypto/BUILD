# This package contains the build rule to build elemental2-dom.
load(
    "//build_defs/internal_do_not_use:elemental_utils.bzl",
    "patch_extern_file",
)
load("@com_google_jsinterop_generator//:jsinterop_generator.bzl", "jsinterop_generator")

package(
    default_applicable_licenses = ["//:license"],
    default_visibility = [
        "//:__subpackages__",
    ],
    # Apache2
    licenses = ["notice"],
)

patch_extern_file(
    name = "w3c_webcrypto_patched",
    src = "//third_party:w3c_webcrypto.js",
    patch_file = "w3c_webcrypto.js.diff",
)

filegroup(
    name = "externs",
    srcs = [
        ":w3c_webcrypto_patched",
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
