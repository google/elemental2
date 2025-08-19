# Elemental2 &middot; ![Latest Release](https://img.shields.io/github/v/release/google/elemental2) &middot; [![Build Status](https://github.com/google/elemental2/actions/workflows/ci.yaml/badge.svg)](https://github.com/google/elemental2/actions/workflows/ci.yaml)

Elemental2 provides type checked access to all browser APIs for Java code. This
is done by using [closure extern files](https://github.com/google/closure-compiler/tree/master/externs)
and generating JsTypes, which are part of the [new JsInterop specification](https://goo.gl/agme3T)
that is both implemented in GWT and J2CL.

Bazel dependencies
------------------
Using Bazel 8 or later, add to your \`MODULE.bazel\` file:

```starlark
bazel_dep(name = "elemental2", version = "<RELEASE_VERSION>")
```

Replace `RELEASE_VERSION` with an actual
[release version](https://github.com/google/elemental2/releases):

Now from you can add elemental2 targets as needed to your `j2cl_library` deps.

Following are the different elemental2 modules and their target names:

 module     | Bazel targets for J2CL
 -----------| -----------------------
 core       | `@elemental2//:elemental2-core-j2cl`
 dom        | `@elemental2//:elemental2-dom-j2cl`
 promise    | `@elemental2//:elemental2-promise-j2cl`
 indexeddb  | `@elemental2//:elemental2-indexeddb-j2cl`
 svg        | `@elemental2//:elemental2-svg-j2cl`
 webgl      | `@elemental2//:elemental2-webgl-j2cl`
 media      | `@elemental2//:elemental2-media-j2cl`
 webstorage | `@elemental2//:elemental2-webstorage-j2cl`

Maven dependencies
------------------
If your project uses [Maven](https://maven.apache.org), add the following maven
dependencies in your `pom.xml`. Replace `RELEASE_VERSION` with an actual
[release version](https://github.com/google/elemental2/releases):

    <dependency>
      <groupId>com.google.elemental2</groupId>
      <artifactId>${artifact-id}</artifactId>
      <version>RELEASE_VERSION</version>
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
