Contributions
--------------
## Contributor License Agreements

Before to propose a patch please fill out either the individual or corporate Contributor License Agreement (CLA).

  * If you are an individual writing original source code and you're sure you own the intellectual property, then you'll need to sign an [individual CLA](http://code.google.com/legal/individual-cla-v1.0.html).
  * If you work for a company that wants to allow you to contribute your work, then you'll need to sign a [corporate CLA](http://code.google.com/legal/corporate-cla-v1.0.html).

Follow either of the two links above to access the appropriate CLA and instructions for how to sign and return it. Once we receive it, we'll be able to accept your pull requests.

## Contributing A Patch

The code is generated with the [jsinterop generator](https://www.github.com/google/jsinterop-generator) and is based on the extern files defined in the [closure-compiler project](https://github.com/google/closure-compiler/tree/master/externs).
They are several ways to improve the generated code:

1. Submit an issue describing your proposed change to the repo in question.
1. The repo owner will respond to your issue.
1. Fork the desired repo.
1. Implement and test your change:
    - If you want to fix/improve an existing API, modify the externs files of the [closure-compiler project](https://github.com/google/closure-compiler/tree/master/externs)
    - If the generated code is incorrect, modify the [jsinterop generator project](https://www.github.com/google/jsinterop-generator)

1. Open a pull request in the right repository.

## Smooth development experience.

This repository is strongly linked to two other repositories:
    - the [jsinterop generator project](https://www.github.com/google/jsinterop-generator)
    - the [closure-compiler project](https://github.com/google/closure-compiler)

For a smooth developer experience with [Bazel](https://bazel.build/), we recommend you to check-in locally both projects and modify the elemental2 `WORSPACE` in order to refer directly to your local repositories.
For that purpose, add the following lines at the beginning of the WORKSPACE file (after the workspace definition):

    workspace(name = "com_google_elemental2")

    # add the code below just after the workspace definition

    new_local_repository(
        name = "com_google_closure_compiler",
        path = "/path/to/closure-compiler/repo",
        build_file = "jscomp.BUILD",
    )

    local_repository(
        name = "com_google_jsinterop_generator",
        path = "/path/to/jsinterop-generator/repo",
    )

Be sure to replace `/path/to/closure-compiler/repo` and `/path/to/jsinterop-generator/repo` accordingly.


You can now implement local modifications in both project and ask Bazel to build elemental2 with those modifications.

