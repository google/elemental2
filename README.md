Elemental2
===================

Elemental2 provides type checked access to all browser APIs for Java code. This
is done by using [closure extern files](https://github.com/google/closure-compiler/tree/master/externs)
and generating JsTypes, which are part of the [new JsInterop specification](https://goo.gl/agme3T)
that is both implemented in GWT and J2CL.

Build with bazel
-----------------
If you want to modify and/or build the last version on your own, follow the instructions below:

- Install [Bazel](https://bazel.build/versions/master/docs/install.html).
- clone this repository with git: `git clone https://github.com/google/elemental2.git`
- Inside the repository, run the script `build_gwt_mvn_jars.sh`:

      $ ./build_gwt_mvn_jars.sh /path/to/output/directory 1.0.0-myversion-p1

The jars are available in `/path/to/output/directory` directory using a maven2 layout with the
version specified `1.0.0-myversion-p1`.

Bazel dependencies
------------------
If your project use [Bazel](https://bazel.build), add this repository as an external dependency in your `WORKSPACE` file

      new_http_archive(
        name = "com_google_elemental2",
        url="https://github.com/google/elemental2/archive/master.zip",
      )

Elemental2 is split up in several jars files. Refer to the following targets in your `java_library` deps:

 module | Bazel targets
 ------ | -------------
 core | `@com_google_elemental2//java/elemental2/core`
 dom | `@com_google_elemental2//java/elemental2/dom`
 promise | `@com_google_elemental2//java/elemental2/promise`
 indexeddb | `@com_google_elemental2//java/elemental2/indexeddb`
 svg | `@com_google_elemental2//java/elemental2/svg`
 webgl | `@com_google_elemental2//java/elemental2/webgl`
 media | `@com_google_elemental2//java/elemental2/media`
 webstorage | `@com_google_elemental2//java/elemental2/webstorage`

Maven dependencies
------------------
If your project use [Maven](https://maven.apache.org), add maven dependencies in your `pom.xml`:

    <dependency>
      <groupId>com.google.elemental2</groupId>
      <artifactId>${artifact-id}</artifactId>
      <version>1.0.0-RC1</version>
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
 core | [elemental2-core.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-core/1.0.0-RC1/elemental2-core-1.0.0-RC1.jar)
 dom | [elemental2-dom.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-dom/1.0.0-RC1/elemental2-dom-1.0.0-RC1.jar)
 promise | [elemental2-promise.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-promise/1.0.0-RC1/elemental2-promise-1.0.0-RC1.jar)
 indexeddb | [elemental2-indexeddb.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-indexeddb/1.0.0-RC1/elemental2-indexeddb-1.0.0-RC1.jar)
 svg | [elemental2-svg.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-svg/1.0.0-RC1/elemental2-svg-1.0.0-RC1.jar)
 webgl | [elemental2-webgl.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-webgl/1.0.0-RC1/elemental2-webgl-1.0.0-RC1.jar)
 media | [elemental2-media.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-media/1.0.0-RC1/elemental2-media-1.0.0-RC1.jar)
 webstorage | [elemental2-webstorage.jar](https://oss.sonatype.org/content/repositories/releases/com/google/elemental2/elemental2-webstorage/1.0.0-RC1/elemental2-webstorage-1.0.0-RC1.jar)

GWT
---
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

Contributing
------------
Please refer to [the contributing document](CONTRIBUTING.md).

Licensing
---------
Please refer to [the license file](LICENSE).

Disclaimer
----------
This is not an official Google product.
