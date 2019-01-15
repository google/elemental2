Contributions
--------------
## Contributor License Agreements

Before to propose a patch please fill out either the individual or corporate Contributor License Agreement (CLA).

  * If you are an individual writing original source code and you're sure you own the intellectual property, then you'll need to sign an [individual CLA](http://code.google.com/legal/individual-cla-v1.0.html).
  * If you work for a company that wants to allow you to contribute your work, then you'll need to sign a [corporate CLA](http://code.google.com/legal/corporate-cla-v1.0.html).

Follow either of the two links above to access the appropriate CLA and instructions for how to sign and return it. Once we receive it, we'll be able to accept your pull requests.

## Contributing A Patch

The code is generated with the [jsinterop generator][1] and is based on the extern files defined in the [closure-compiler project].

### Fix code generation

If you want to fix the way the code is generated, changes need to be done in the [jsinterop generator project][1].

### Fix existing API
If you want to fix/improve an existing API, modifications need to be done in the externs files of the [closure-compiler project]

### Add a missing API
If you want to add an API that is not part of Elemental2, you need to search in [closure-compiler project] if an existing extern file already defines this API.

* If there is an existing extern file defining this API:
  - Add @deprecated to all old APIs in the extern file and send a pull request to [closure-compiler project].
  - Change corresponding Elemental BUILD file to include that extern file.
  - Fix number conversion by following [these instructions](#fix-number-conversion) for all API defined in this file.
  - Fix parameter names by following [these instructions](#fix-parameter-names) for all API defined in this file.

* If there is no file defining this API:
  - Add the API in one of the existing files and send a pull request to [closure-compiler project].
  - Fix number conversion by following [these instructions](#fix-number-conversion) for this specific API.
  - Fix parameter names by following [these instructions](#fix-parameter-names) for this specific API.


### Fix number conversion
By default, the jsinterop generator will convert all `number` javascript type to `double` when generating the java classes.
In order to know if an API is accepting integer number, you need to refer to the API official specification. If the type is any of `byte`, `octet`, `short`, `long`, `long long`, it needs to be converted to `int`.

For that purpose, it's possible to tell the generator to convert some `number` type to `int`:
 - Create an `integer_entities.txt` file and add it to `jsinterop_generator` rule of the related `BUILD` file (if it doesn't already exist).
 - Open the file and list the API whose type need to be converted to `int`:
   - for fields and method return types, use the java fully qualified name of the method or field (e.g. elemental2.dom.Baz.foo).
   - for a method's parameter, use the java fully qualified name of the parameter (e.g elemental2.dom.Baz.foo.param1)`

For an example, see [integer_entities.txt file](https://github.com/google/elemental2/blob/master/java/elemental2/dom/integer_entities.txt) for elemental2-dom.

### Fix parameter names
In closure type system, function types like `{function(number):boolean}` cannot specify a name for their parameter. As a result, the jsinterop generator needs to generate a generic name for those parameters that are not meaningful.

It's possible to tell the generator to use a more specific name for those parameter:
 - Create an `name_mappings.txt` file and add it to `jsinterop_generator` rule of the related `BUILD` file (if it doesn't already exist).
 - Open the file and add an entry for each parameter using the following pattern: `java_fqn_of_the_parameter=newName`
 
Let's take an example. If you have the following generated code:

```java
package foo;

class Bar {
  interface BazCallback {
    void onInvoke(boolean p0);
  }
}

```

You can rename the `onInvoke` `p0` parameter to `newName` by adding the following entry:
`foo.Bar.BazCallback.onInvoke.p0=newName`

For an example, see [name_mappings.txt file](https://github.com/google/elemental2/blob/master/java/elemental2/dom/name_mappings.txt) of elemental2-dom.

## Smooth development experience.

This repository is strongly linked to two other repositories:
  - the [jsinterop generator project][1]
  - the [closure-compiler project]

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


[closure-compiler project]:https://github.com/google/closure-compiler/tree/master/externs
[1]:https://www.github.com/google/jsinterop-generator
