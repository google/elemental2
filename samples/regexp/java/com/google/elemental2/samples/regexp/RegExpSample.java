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
package com.google.elemental2.samples.regexp;

import static elemental2.dom.DomGlobal.document;
import static elemental2.dom.DomGlobal.window;

import elemental2.core.JsRegExp;
import elemental2.core.JsString;
import elemental2.core.RegExpResult;
import elemental2.dom.Element;
import elemental2.dom.HTMLInputElement;
import jsinterop.annotations.JsMethod;
import jsinterop.base.Js;

/** Entry point. */
public class RegExpSample {
  @JsMethod
  public static void install() {
    window.addEventListener("load", evt -> onLoad());
  }

  private static void onLoad() {
    installUi();

    document
        .getElementsByTagName("form")
        .getAt(0)
        .addEventListener(
            "submit",
            evt -> {
              evt.preventDefault();
              onFormSubmitted();
            });
  }

  private static void installUi() {
    Element section = document.createElement("section");

    section.innerHTML =
        "<form>"
            + "  <label for='regexp'>Regexp</label>"
            + "  <input id='regexp' type='text'>"
            + "  <label for='testText'>Text to test</label>"
            + "  <input id='testText' type='text'>"
            + "  <button type='submit'>test</button>"
            + "</form>"
            + "<div id='results'></div>";

    document.body.appendChild(section);
  }

  private static void onFormSubmitted() {
    HTMLInputElement testTextInput = (HTMLInputElement) document.getElementById("testText");
    HTMLInputElement regExpInput = (HTMLInputElement) document.getElementById("regexp");

    clearResults();

    JsRegExp regExp = new JsRegExp(regExpInput.value, "g");

    addResult("RegExp.prototype.test()", regExp.test(testTextInput.value));

    RegExpResult execs = regExp.exec(testTextInput.value);

    if (execs == null || execs.length == 0) {
      addResult("RegExp.prototype.exec()", "null");
    } else {
      int i = 0;
      for (String exec : execs.asList()) {
        addResult("RegExp.prototype.exec()[" + i + "]", exec);
        i++;
      }
    }

    JsString string = Js.uncheckedCast(testTextInput.value);
    String[] matches = string.match(regExp);
    if (matches == null || matches.length == 0) {
      addResult("String.prototype.match()", "null");
    } else {
      int i = 0;
      for (String match : matches) {
        addResult("String.prototype.match()[" + i + "]", match);
        i++;
      }
    }

    addResult("RegExp.prototype.source", regExp.source);
    addResult("RegExp.prototype.multiline", regExp.multiline);
    addResult("RegExp.prototype.global", regExp.global);
    addResult("RegExp.prototype.ignoreCase", regExp.ignoreCase);
    addResult("RegExp.prototype.lastIndex", regExp.lastIndex);
  }

  private static void addResult(String label, Object value) {
    Element div = document.createElement("div");
    div.textContent = label + ": " + value;

    document.getElementById("results").appendChild(div);
  }

  private static void clearResults() {
    document.getElementById("results").innerHTML = "";
  }
}
