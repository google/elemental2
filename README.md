# Elemental2 &middot; [![Build Status](https://github.com/google/elemental2/actions/workflows/ci.yaml/badge.svg)](https://github.com/google/elemental2/actions/workflows/ci.yaml)

Elemental2 provides type checked access to all browser APIs for Java code. This
is done by using [closure extern files](https://github.com/google/closure-compiler/tree/master/externs)
and generating JsTypes, which are part of the [new JsInterop specification](https://goo.gl/agme3T)
that is both implemented in GWT and J2CL.

Bazel dependencies
------------------
If your project uses [Bazel](https://bazel.build), add this repository as an
external dependency in your `WORKSPACE` file:

```
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "com_google_elemental2",
    strip_prefix = "elemental2-1.1.0",
    url = "https://github.com/google/elemental2/archive/1.1.0.zip",
)

load("@com_google_elemental2//build_defs:repository.bzl", "load_elemental2_repo_deps")
load_elemental2_repo_deps()

load("@com_google_elemental2//build_defs:workspace.bzl", "setup_elemental2_workspace")
setup_elemental2_workspace()
```

Now from you can add elemental2 targets as needed to your `j2cl_library` deps.

Following are the different elemental2 modules and their target names:

 module     | Bazel targets for J2CL
 -----------| -----------------------
 core       | `@com_google_elemental2//:elemental2-core-j2cl`
 dom        | `@com_google_elemental2//:elemental2-dom-j2cl`
 promise    | `@com_google_elemental2//:elemental2-promise-j2cl`
 indexeddb  | `@com_google_elemental2//:elemental2-indexeddb-j2cl`
 svg        | `@com_google_elemental2//:elemental2-svg-j2cl`
 webgl      | `@com_google_elemental2//:elemental2-webgl-j2cl`
 media      | `@com_google_elemental2//:elemental2-media-j2cl`
 webstorage | `@com_google_elemental2//:elemental2-webstorage-j2cl`

Maven dependencies
------------------
If your project uses [Maven](https://maven.apache.org), add the following maven
dependencies in your `pom.xml`:

    <dependency>
      <groupId>com.google.elemental2</groupId>
      <artifactId>${artifact-id}</artifactId>
      <version>1.1.0</version>
    </dependency>


 module | artifact-id
 ------ | -----------
 core | `elemental2-core`
 dom | `elemental2-dom`
 promise | `elemental2-promise`
 indexeddb | `elemental2-indexeddb`
 svg | `elemental2-svg`
 webgl | `elemental2-webgl`
 media | `elemental2-media`
 webstorage | `elemental2-webstorage`

Download the jar files
----------------------
You can also download manually the jars files.

 module | jar file
 ------ | --------
 core | [elemental2-core.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-core/1.1.0/elemental2-core-1.1.0.jar)
 dom | [elemental2-dom.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-dom/1.1.0/elemental2-dom-1.1.0.jar)
 promise | [elemental2-promise.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-promise/1.1.0/elemental2-promise-1.1.0.jar)
 indexeddb | [elemental2-indexeddb.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-indexeddb/1.1.0/elemental2-indexeddb-1.1.0.jar)
 svg | [elemental2-svg.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-svg/1.1.0/elemental2-svg-1.1.0.jar)
 webgl | [elemental2-webgl.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-webgl/1.1.0/elemental2-webgl-1.1.0.jar)
 media | [elemental2-media.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-media/1.1.0/elemental2-media-1.1.0.jar)
 webstorage | [elemental2-webstorage.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-webstorage/1.1.0/elemental2-webstorage-1.1.0.jar)

GWT
---

> Elemental2 v1.0.0+ requires GWT 2.9 or above.

If you use Elemental2 with [GWT](http://www.gwtproject.org/), you need to inherit the right gwt module in your `gwt.xml` file:

 module | GWT module name
 ------ | ---------------
 core | `elemental2.core.Core`
 dom | `elemental2.dom.Dom`
 promise | `elemental2.promise.Promise`
 indexeddb | `elemental2.indexeddb.IndexedDb`
 svg | `elemental2.svg.Svg`
 webgl | `elemental2.webgl.WebGl`
 media | `elemental2.media.Media`
 webstorage | `elemental2.webstorage.WebStorage`


Build GWT compatible maven jar files
------------------------------------
If you want to modify and/or build the last version on your own, follow the instructions below:

- Install [Bazelisk](https://github.com/bazelbuild/bazelisk):

```shell
    $ npm install -g @bazel/bazelisk
    $ alias bazel=bazelisk
```
- Clone this git repository:
  ```shell
  $ git clone https://github.com/google/elemental2.git
  ```
- Run the release script:
  ```shell
      $ cd elemental2
      $ ./maven/release_elemental.sh --version local --no-deploy
  ```

The script will output the directory containing the generated jar files that
can be used with maven.

Contributing
------------
Please refer to [the contributing document](CONTRIBUTING.md).

Licensing
---------
Please refer to [the license file](LICENSE).

Disclaimer
----------
This is not an official Google product.
