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

#  Patches for w3c_dom2.js and window.js: Add standard api of Window object that are not defined in
#  w3c_dom*.js but in browsers specific extern files. We've tried to fix that in the extern
#  files directly (http://cl/124712783) but moving API from one file to another make things
#  typechecked differently (but correctly) and breaks several apps. This cl will be roll forward
#  when the broken apps get fixed and the patch should be deleted.
patch_extern_file(
    name = "w3c_dom2_patched",
    src = "//third_party:w3c_dom2.js",
    patch_file = "w3c_dom2.js.diff",
)

# Patch for window.js:
#   - Improve setTimeout, setInterval methods for generating better java API.
#  TODO(b/36558100): cleanup extern file directly
patch_extern_file(
    name = "window_patched",
    src = "//third_party:window.js",
    patch_file = "window.js.diff",
)

filegroup(
    name = "externs",
    srcs = [
        # file order matters.
        "//third_party:w3c_event.js",
        "//third_party:w3c_event3.js",
        "//third_party:w3c_device_sensor_event.js",
        "//third_party:w3c_touch_event.js",
        "//third_party:w3c_trusted_types.js",
        "//third_party:w3c_dom1.js",
        ":w3c_dom2_patched",
        "//third_party:w3c_dom3.js",
        "//third_party:w3c_dom4.js",
        "//third_party:w3c_batterystatus.js",
        "//third_party:w3c_fileapi.js",
        "//third_party:page_visibility.js",
        "//third_party:w3c_rtc.js",
        "//third_party:html5.js",
        ":window_patched",
        "//third_party:w3c_range.js",
        "//third_party:w3c_geometry1.js",
        "//third_party:w3c_css.js",
        "//third_party:w3c_css3d.js",
        "//third_party:w3c_xml.js",
        "//third_party:flash.js",
        "//third_party:w3c_anim_timing.js",
        # TODO(dramaix): remove old webkit api and rename file to w3c_notifications.js
        "//third_party:webkit_notifications.js",
        "//third_party:mediasource.js",
        "//third_party:streamsapi.js",
        "//third_party:url.js",
        "//third_party:w3c_abort.js",
        "//third_party:fetchapi.js",
        "//third_party:whatwg_console.js",
        "//third_party:w3c_serviceworker.js",
        "//third_party:w3c_navigation_timing.js",
        "//third_party:w3c_clipboardevent.js",
        "//third_party:w3c_eventsource.js",
        "//third_party:w3c_elementtraversal.js",
        "//third_party:w3c_selection.js",
        "//third_party:w3c_selectors.js",
        "//third_party:w3c_geolocation.js",
        "//third_party:w3c_requestidlecallback.js",
    ],
)

java_library(
    name = "EventListenerCleaner",
    srcs = ["EventListenerCleaner.java"],
    visibility = ["//visibility:public"],
    deps = [
        "//third_party:guava",
        "@com_google_jsinterop_generator//java/jsinterop/generator/model",
    ],
)

# Dom part of elemental
# order of files in srcs attributes matters!
jsinterop_generator(
    name = "dom",
    srcs = [":externs"],
    custom_preprocessing_pass = [
        "elemental2.dom.EventListenerCleaner",
    ],
    integer_entities_files = ["integer_entities.txt"],
    # override auto generated js_deps since modified externs cause conflicts
    j2cl_js_deps = [],
    name_mapping_files = ["name_mappings.txt"],
    runtime_deps = [
        ":EventListenerCleaner",
    ],
    deps = [
        "//java/elemental2/core",
        "//java/elemental2/promise",
    ],
)
