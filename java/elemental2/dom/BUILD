# This package contains the build rule to build elemental2-dom.

package(default_visibility = [
    "//:__subpackages__",
])

# Apache2
licenses(["notice"])

load(
    "//build_defs/internal_do_not_use:elemental_utils.bzl",
    "patch_extern_file",
)
load("@com_google_jsinterop_generator//:jsinterop_generator.bzl", "jsinterop_generator")

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

# Patch for fileapi.js: Remove old API for local FileSystem
patch_extern_file(
    name = "fileapi_patched",
    src = "//third_party:fileapi.js",
    patch_file = "fileapi.js.diff",
)

#  Patch for html5.js: Remove reference to ClipboardData that is an api from Internet Explorer
#  and should not be part from Elemental. There is a dependency to this type in html5.js
#  where DataTransfer extends ClipboardData. We've made an attempt to break
#  this dependency (http://cl/124542026) but that breaks apps (http://b/29263729).
patch_extern_file(
    name = "html5_patched",
    src = "//third_party:html5.js",
    patch_file = "html5.js.diff",
)

# Patch for window.js:
#   - add window global variable
#   - Remove netscape only global variable.
#   - Improve setTimeout, setInterval methods for generating better java API.
#  TODO(b/36558100): cleanup extern file directly
patch_extern_file(
    name = "window_patched",
    src = "//third_party:window.js",
    patch_file = "window.js.diff",
)

# Patch for w3c_event.js: Simplify addEventListener/removeEventListener by removing the
# Eventlistener function that returns a boolean. User can use event.preventDefault().
# TODO(b/36591631): Add a mechanism in the jsinterop generator that modifies method overloading
# (or modifies somehow the closure ast), so that we don't have to patch the extern file
patch_extern_file(
    name = "w3c_event_patched",
    src = "//third_party:w3c_event.js",
    patch_file = "w3c_event.js.diff",
)

# Patch for w3c_navigation_timing.js: Remove Performance.prototype.memory available only in blink.
patch_extern_file(
    name = "w3c_navigation_timing_patched",
    src = "//third_party:w3c_navigation_timing.js",
    patch_file = "w3c_navigation_timing.js.diff",
)

# Patch for w3c_serviceworker.js: Remove ServiceWorkerGlobalScope.prototype.indexedDB thatis not
# part of the spec and introduce a cyclic dependency toelemental/indexeddb
patch_extern_file(
    name = "w3c_serviceworker_patched",
    src = "//third_party:w3c_serviceworker.js",
    patch_file = "w3c_serviceworker.js.diff",
)

filegroup(
    name = "externs",
    srcs = [
        # file order matters.
        ":w3c_event_patched",
        "//third_party:w3c_event3.js",
        "//third_party:w3c_device_sensor_event.js",
        "//third_party:w3c_touch_event.js",
        "//third_party:w3c_trusted_types.js",
        "//third_party:w3c_dom1.js",
        ":w3c_dom2_patched",
        "//third_party:w3c_dom3.js",
        "//third_party:w3c_dom4.js",
        "//third_party:w3c_batterystatus.js",
        ":fileapi_patched",
        "dom_missing_api.js",
        "//third_party:page_visibility.js",
        "//third_party:w3c_rtc.js",
        ":html5_patched",
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
        "//third_party:streamsapi.js",
        "//third_party:url.js",
        "//third_party:w3c_abort.js",
        "//third_party:fetchapi.js",
        "//third_party:whatwg_console.js",
        ":w3c_serviceworker_patched",
        ":w3c_navigation_timing_patched",
        "//third_party:w3c_clipboardevent.js",
        "//third_party:w3c_eventsource.js",
        "//third_party:w3c_elementtraversal.js",
        "//third_party:w3c_selectors.js",
        "//third_party:w3c_geolocation.js",
        "//third_party:w3c_requestidlecallback.js",
    ],
)

# Dom part of elemental
# order of files in srcs attributes matters!
jsinterop_generator(
    name = "dom",
    srcs = [":externs"],
    integer_entities_files = ["integer_entities.txt"],
    # override auto generated js_deps since modified externs cause conflicts
    j2cl_js_deps = [],
    name_mapping_files = ["name_mappings.txt"],
    deps = [
        "//java/elemental2/core",
        "//java/elemental2/promise",
    ],
)
