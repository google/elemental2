/*
 * Copyright 2017 Google Inc.
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

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 * Base contract of IThenable promise provided for compatibility with non-official Promise
 * implementations.
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface IThenable<T> {
  @JsFunction
  interface ThenOnFulfilledCallbackFn<T, V> {
    IThenable<V> onInvoke(T p0);
  }

  @JsFunction
  interface ThenOnRejectedCallbackFn<V> {
    IThenable<V> onInvoke(Object p0);
  }

  <V> IThenable<V> then(ThenOnFulfilledCallbackFn<? super T, ? extends V> onFulfilled);

  <V> IThenable<V> then(
      ThenOnFulfilledCallbackFn<? super T, ? extends V> onFulfilled,
      ThenOnRejectedCallbackFn<? extends V> onRejected);
}
