package(default_visibility = [
    "//:__subpackages__",
])

load(":extern.bzl", "extern")

licenses(["notice"])  # Apache2

extern(
    name = "es3",
)

extern(
    name = "es5",
)

extern(
    name = "es6",
)

extern(
    name = "es6_collections",
)

extern(
    name = "w3c_event",
    path = "browser/w3c_event.js",
)

extern(
    name = "w3c_event3",
    path = "browser/w3c_event3.js",
)

extern(
    name = "w3c_device_sensor_event",
    path = "browser/w3c_device_sensor_event.js",
)

extern(
    name = "w3c_touch_event",
    path = "browser/w3c_touch_event.js",
)

extern(
    name = "w3c_trusted_types",
    path = "browser/w3c_trusted_types.js",
)

extern(
    name = "w3c_dom1",
    path = "browser/w3c_dom1.js",
)

extern(
    name = "w3c_dom2",
    path = "browser/w3c_dom2.js",
)

extern(
    name = "w3c_dom3",
    path = "browser/w3c_dom3.js",
)

extern(
    name = "w3c_dom4",
    path = "browser/w3c_dom4.js",
)

extern(
    name = "w3c_batterystatus",
    path = "browser/w3c_batterystatus.js",
)

extern(
    name = "w3c_fileapi",
    path = "browser/w3c_fileapi.js",
)

extern(
    name = "page_visibility",
    path = "browser/page_visibility.js",
)

extern(
    name = "w3c_rtc",
    path = "browser/w3c_rtc.js",
)

extern(
    name = "html5",
    path = "browser/html5.js",
)

extern(
    name = "window",
    path = "browser/window.js",
)

extern(
    name = "w3c_range",
    path = "browser/w3c_range.js",
)

extern(
    name = "w3c_geometry1",
    path = "browser/w3c_geometry1.js",
)

extern(
    name = "w3c_css",
    path = "browser/w3c_css.js",
)

extern(
    name = "w3c_css3d",
    path = "browser/w3c_css3d.js",
)

extern(
    name = "w3c_xml",
    path = "browser/w3c_xml.js",
)

extern(
    name = "flash",
    path = "browser/flash.js",
)

extern(
    name = "w3c_anim_timing",
    path = "browser/w3c_anim_timing.js",
)

extern(
    name = "webkit_notifications",
    path = "browser/webkit_notifications.js",
)

extern(
    name = "w3c_abort",
    path = "browser/w3c_abort.js",
)

extern(
    name = "fetchapi",
    path = "browser/fetchapi.js",
)

extern(
    name = "w3c_serviceworker",
    path = "browser/w3c_serviceworker.js",
)

extern(
    name = "w3c_requestidlecallback",
    path = "browser/w3c_requestidlecallback.js",
)

extern(
    name = "w3c_navigation_timing",
    path = "browser/w3c_navigation_timing.js",
)

extern(
    name = "w3c_clipboardevent",
    path = "browser/w3c_clipboardevent.js",
)

extern(
    name = "w3c_eventsource",
    path = "browser/w3c_eventsource.js",
)

extern(
    name = "w3c_elementtraversal",
    path = "browser/w3c_elementtraversal.js",
)

extern(
    name = "w3c_selectors",
    path = "browser/w3c_selectors.js",
)

extern(
    name = "w3c_geolocation",
    path = "browser/w3c_geolocation.js",
)

extern(
    name = "w3c_indexeddb",
    path = "browser/w3c_indexeddb.js",
)

extern(
    name = "w3c_audio",
    path = "browser/w3c_audio.js",
)

extern(
    name = "webgl",
    path = "browser/webgl.js",
)

extern(
    name = "webstorage",
    path = "browser/webstorage.js",
)

extern(
    name = "whatwg_console",
    path = "browser/whatwg_console.js",
)

extern(
    name = "mediakeys",
    path = "browser/mediakeys.js",
)

extern(
    name = "streamsapi",
    path = "browser/streamsapi.js",
)

extern(
    name = "url",
    path = "browser/url.js",
)

extern(
    name = "mediasource",
    path = "browser/mediasource.js",
)

extern(
    name = "w3c_selection",
    path = "browser/w3c_selection.js",
)

# svg.js and webassembly.js are not part of the common externs provided by the
# js-compiler binary.
alias(
    name = "svg.js",
    actual = "@com_google_closure_compiler//:contrib/externs/svg.js",
)

# TODO(dramaix): This file will be included in the extern files of closure compiler
# release. When we update to a newer version of closure compiler, change alias rule
# to an extern rule.
alias(
    name = "webassembly.js",
    actual = "@com_google_closure_compiler//:externs/browser/webassembly.js",
)

alias(
    name = "w3c_worklets.js",
    actual = "@com_google_closure_compiler//:externs/browser/w3c_worklets.js",
)

alias(
    name = "jsinterop-annotations",
    actual = "@com_google_j2cl//:jsinterop-annotations-j2cl",
)

alias(
    name = "jsinterop-base",
    actual = "@com_google_jsinterop_base//:jsinterop-base-j2cl",
)

java_library(
    name = "gwt-array-stamper",
    srcs = [
        "@org_gwtproject_gwt//user:super/com/google/gwt/emul/javaemul/internal/ArrayStamper.java",
    ],
)

java_library(
    name = "guava",
    exports = ["@com_google_guava"],
)
