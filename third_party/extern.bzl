"""Utility macro used for extracting an specific extern file from js-compiler binary."""

def extern(name, path = None):
    """Extract a specific extern file from the extern files provided by the jscompiler binary"""
    if not path:
        path = "%s.js" % name

    native.genrule(
        name = name,
        srcs = ["@com_google_javascript_closure_compiler//:externs"],
        outs = ["%s.js" % name],
        tools = ["@bazel_tools//tools/zip:zipper"],
        cmd = "$(location @bazel_tools//tools/zip:zipper) x  $(location @com_google_javascript_closure_compiler//:externs) %s; mv %s $@" % (path, path),
    )
