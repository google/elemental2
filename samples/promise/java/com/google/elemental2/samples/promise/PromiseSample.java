/*
 * Copyright 2019 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.elemental2.samples.promise;

import static elemental2.dom.DomGlobal.document;
import static elemental2.dom.DomGlobal.setTimeout;
import static elemental2.dom.DomGlobal.window;

import elemental2.core.JsMath;
import elemental2.dom.Element;
import elemental2.promise.Promise;
import jsinterop.annotations.JsMethod;

/** Entry point. */
public class PromiseSample {
  @JsMethod
  public static void install() {
    window.addEventListener("load", evt -> onLoad());
  }

  // GWT entry point
  static void onLoad() {
    installUi();

    Promise<String> promise =
        new Promise<>(
            (resolve, reject) -> {
              log("Promise executor starts...");
              setTimeout((args) -> resolve.onInvoke("Promise resolved"), JsMath.random() * 2000);
              log("log(\"Promise executor ends...\");");
            });

    promise
        .then(
            (value) -> {
              log(value);
              return Promise.resolve(value + " again");
            })
        .then(
            (value) -> {
              log(value);
              return Promise.reject("A fake error occurs");
            })
        .catch_(
            (error) -> {
              log("" + error);
              return null;
            });
  }

  private static void installUi() {
    Element section = document.createElement("section");

    Element logs = document.createElement("div");
    logs.setAttribute("id", "logs");

    section.appendChild(logs);

    document.body.appendChild(section);
  }

  private static void log(String msg) {
    Element div = document.createElement("div");
    div.textContent = msg;

    document.getElementById("logs").appendChild(div);
  }
}
