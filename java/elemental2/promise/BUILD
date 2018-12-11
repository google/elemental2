# This package contains the harcoded source for Promise API.
# This API is hard to generate automaticaly with the JsInterop generator
# and the resulting API is not user friendly

package(default_visibility = [
    "//:__subpackages__",
])

# Apache2
licenses(["notice"])

load("@com_google_jsinterop_generator//:jsinterop_generator_import.bzl", "jsinterop_generator_import")

jsinterop_generator_import(
    name = "promise",
    srcs = glob(["*.java"]),
    externs_srcs = ["promise.js"],
    gwt_module_name = "elemental2.promise.Promise",
    gwt_xml = "Promise.gwt.xml",
    types_mapping_files = ["promise.types"],
)
