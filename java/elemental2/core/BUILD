# This package contains the build rule to build elemental2-core.

package(
    default_visibility = [
        "//:__subpackages__",
    ],
    # Apache2
    licenses = ["notice"],
)

load(
    "//build_defs/internal_do_not_use:elemental_utils.bzl",
    "patch_extern_file",
)
load("@com_google_jsinterop_generator//:jsinterop_generator.bzl", "jsinterop_generator")

# Removes non-existing static methods defined in Array type.
# TODO(b/36088884): clean-up the extern file directly
# Make RegExp API stricter.
# TODO(b/38034193): clean up the RegExp API in the extern file directly.
patch_extern_file(
    name = "es3_patched",
    src = "//third_party:es3.js",
    patch_file = "es3.js.diff",
)

filegroup(
    name = "externs",
    srcs = [
        # order matters
        ":es3_patched",
        "//third_party:es5.js",
        "//third_party:es6.js",
        "//third_party:es6_collections.js",
    ],
)

java_library(
    name = "core-visitors",
    srcs = ["BuiltInClosureTypeCleaner.java"],
    visibility = ["//visibility:public"],
    deps = [
        "//java/com/google/common/base",
        "//java/com/google/common/collect:collect-gwt",
        "@com_google_jsinterop_generator///java/jsinterop/generator/model",
    ],
)

# Core of Elemental containing basic javascript types definitions
jsinterop_generator(
    name = "core",
    srcs = [":externs"],
    custom_preprocessing_pass = [
        "elemental2.core.BuiltInClosureTypeCleaner",
    ],
    global_scope_class_name = "Global",
    gwt_java_deps = [
        "//third_party:gwt-array-stamper",
    ],
    integer_entities_files = ["integer_entities.txt"],
    # override auto generated js_deps since modified externs cause conflicts
    j2cl_js_deps = [],
    wildcard_types_files = ["wildcard_types.txt"],
    runtime_deps = [
        ":core-visitors",
    ],
    deps = [
        "//java/elemental2/promise",
    ],
)
