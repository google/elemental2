# Description:
#    Build rules for Elemental. Elemental contains java classes annotated with JsInterop annotations
#    that expose native browsers API.
#

load("@com_google_jsinterop_generator//:jsinterop_generator.bzl", "jsinterop_generator")
load("@bazel_skylib//rules:build_test.bzl", "build_test")

package(
    default_visibility = ["//visibility:public"],
    licenses = ["notice"],
)

exports_files(["LICENSE"])

build_test(
    name = "rule_test",
    targets = [
        ":elemental2-core",
        ":elemental2-promise",
        ":elemental2-dom",
        ":elemental2-svg",
        ":elemental2-webgl",
        ":elemental2-media",
        ":elemental2-webstorage",
        ":elemental2-indexeddb",
    ],
)

# Core of Elemental containing basic javascript types definitions
jsinterop_generator(
    name = "elemental2-core",
    exports = ["//java/elemental2/core"],
)

# Promise API
jsinterop_generator(
    name = "elemental2-promise",
    exports = ["//java/elemental2/promise"],
)

# Dom part of elemental
jsinterop_generator(
    name = "elemental2-dom",
    exports = ["//java/elemental2/dom"],
)

# SVG api
jsinterop_generator(
    name = "elemental2-svg",
    exports = ["//java/elemental2/svg"],
)

# Webgl api
jsinterop_generator(
    name = "elemental2-webgl",
    exports = ["//java/elemental2/webgl"],
)

# Webassembly api
jsinterop_generator(
    name = "elemental2-webassembly",
    exports = ["//java/elemental2/webassembly"],
)

# Media  api
jsinterop_generator(
    name = "elemental2-media",
    exports = ["//java/elemental2/media"],
)

# Web storage api
jsinterop_generator(
    name = "elemental2-webstorage",
    exports = ["//java/elemental2/webstorage"],
)

# Indexed DB api
jsinterop_generator(
    name = "elemental2-indexeddb",
    exports = ["//java/elemental2/indexeddb"],
)
