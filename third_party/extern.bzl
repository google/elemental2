"""Utility macro used for extracting an specific extern file from js-compiler binary."""

def extern(name, path = None):
    """Extract a specific extern file from the extern files provided by the jscompiler binary"""
    if not path:
        path = "%s.js" % name

    native.genrule(
        name = name,
        srcs = ["@com_google_javascript_closure_compiler"],
        outs = ["%s.js" % name],
        tools = ["@bazel_tools//tools/zip:zipper"],
        cmd = """
            TMP=$$(mktemp -d)
            WD=$$(pwd)
            ZIPPER=$$WD/$(location @bazel_tools//tools/zip:zipper)
            # Switch to temp since zipper only works with relative paths.
            cd $$TMP
            $$ZIPPER x $$WD/$(location @com_google_javascript_closure_compiler) externs.zip
            $$ZIPPER x externs.zip %s
            mv $$TMP/%s $$WD/$@
            rm -rf $$TMP
            """ % (path, path),
    )
