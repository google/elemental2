/*
 * Copyright 2026 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package elemental2.promise;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Represents an element of the array returned by Promise.allSettled().
 * @param <VALUE> the type of the succeeded promise.
 */
@JsType(isNative = true, name = "Promise.AllSettledResultElement", namespace = JsPackage.GLOBAL)
@NullMarked
public interface AllSettledResultElement<VALUE extends @Nullable Object> {
    @JsProperty
    String getStatus();

    @JsProperty
    @Nullable
    VALUE getValue();

    @JsProperty
    @Nullable
    Object getReason();
}
