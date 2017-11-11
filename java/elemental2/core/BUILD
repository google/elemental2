# This package contains the build rule to build elemental2-core.

package(default_visibility = [
    "//third_party/java/elemental2:__subpackages__",
    "//:__subpackages__",
])

# Apache2
licenses(["notice"])

load("//:elemental_utils.bzl", "patch_extern_file")
load("@com_google_jsinterop_generator//:jsinterop_generator.bzl", "jsinterop_generator")

# Removes non standard Object methods.
# TODO(b/36088631): clean up es3.js directly
# Removes @this annotations from some Arrays methods. This annotation confuses the generator
# because it redefine the type parameter defined at type level.
# TODO(b/36088887): Handle correctly @this annotation
# Removes non-existing static methods defined in Array type.
# TODO(b/36088884): clean-up the extern file directly
# Define better callback for Strng.replace.
# TODO(b/62258818): remove this specific patch when the bug is fixed and the extern file is updated.
# Make RegExp API stricter.
# TODO(b/38034193): clean up the RegExp API in the extern file directly.
patch_extern_file(
    name = "es3_patched",
    src = "//third_party:es3.js",
    patch_file = "es3.js.diff",
)

# Removes @this annotations from some Arrays methods. This annotation confuses the generator
# because it redefine the type parameter defined at type level.
# TODO(b/36088887): Handle correctly @this annotation
# TODO(b/36252263): Extract Promise API in its own extern file instead
patch_extern_file(
    name = "es6_patched",
    src = "//third_party:es6.js",
    patch_file = "es6.js.diff",
)

# Removes @this annotations from some Set, Map, WeakSet, WeakMap methods. This annotation
# confuses the generator because it redefine the type parameter defined at type level.
# TODO(b/36088887): Handle correctly @this annotation
patch_extern_file(
    name = "es6_collections_patched",
    src = "//third_party:es6_collections.js",
    patch_file = "es6_collections.js.diff",
)

filegroup(
    name = "externs",
    srcs = [
        # order matters
        ":es3_patched",
        "//third_party:es5.js",
        ":es6_patched",
        ":es6_collections_patched",
    ],
)

# Core of Elemental containing basic javascript types definitions
jsinterop_generator(
    name = "core",
    srcs = [":externs"],
    integer_entities_files = ["integer_entities.txt"],
    # These extern definitions are required for the j2cl build test because
    # J2CL refers these symbols in the jre emulation
    j2cl_test_externs_list = ["required_j2cl_externs.js"],
    wildcard_types_files = ["wildcard_types.txt"],
)
