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
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * The Promise object is used for asynchronous computations. A Promise represents a value which may
 * be available now, or in the future, or never.
 *
 * @see <a
 *     href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise">Promise</a>
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
@NullMarked
public class Promise<T extends @Nullable Object> implements IThenable<T> {
  @JsFunction
  public interface FinallyOnFinallyCallbackFn {
    void onInvoke();
  }

  @JsFunction
  public interface CatchOnRejectedCallbackFn<V extends @Nullable Object> {
    @Nullable IThenable<V> onInvoke(Object error);
  }

  @JsFunction
  public interface PromiseExecutorCallbackFn<T extends @Nullable Object> {
    @JsFunction
    interface RejectCallbackFn {
      void onInvoke(Object error);
    }

    @JsFunction
    interface ResolveCallbackFn<T extends @Nullable Object> {
      @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
      interface ResolveUnionType<T extends @Nullable Object> {
        @JsOverlay
        static @Nullable ResolveUnionType<?> of(@Nullable Object o) {
          return Js.cast(o);
        }

        @JsOverlay
        default @Nullable IThenable<T> asIThenable() {
          return Js.cast(this);
        }

        @JsOverlay
        default @Nullable T asT() {
          return Js.cast(this);
        }
      }

      @JsOverlay
      default void onInvoke(@Nullable IThenable<T> value) {
        onInvoke(Js.<ResolveUnionType<T>>uncheckedCast(value));
      }

      void onInvoke(@Nullable ResolveUnionType<T> p0);

      @JsOverlay
      default void onInvoke(@Nullable T value) {
        onInvoke(Js.<ResolveUnionType<T>>uncheckedCast(value));
      }
    }

    void onInvoke(ResolveCallbackFn<T> resolve, RejectCallbackFn reject);
  }

  @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
  public interface ResolveValueUnionType<V extends @Nullable Object> {
    @JsOverlay
    static @Nullable ResolveValueUnionType<?> of(@Nullable Object o) {
      return Js.cast(o);
    }

    @JsOverlay
    default @Nullable IThenable<V> asIThenable() {
      return Js.cast(this);
    }

    @JsOverlay
    default @Nullable V asV() {
      return Js.cast(this);
    }
  }

  @JsOverlay
  public static <V extends @Nullable Object> Promise<V[]> all(IThenable<? extends V>... promises) {
    return allInternal(promises);
  }

  @JsMethod(name = "all")
  private static native <V extends @Nullable Object> Promise<V[]> allInternal(
      IThenable<? extends V>[] promises);

  @JsOverlay
  public static <V extends @Nullable Object> Promise<V> race(IThenable<? extends V>... promises) {
    return raceInternal(promises);
  }

  @JsMethod(name = "race")
  private static native <V extends @Nullable Object> Promise<V> raceInternal(
      IThenable<? extends V>[] promises);

  public static native <V extends @Nullable Object> Promise<V> reject(@Nullable Object error);

  @JsOverlay
  public static final <V extends @Nullable Object> Promise<V> resolve(
      @Nullable IThenable<V> value) {
    return resolve(Js.<ResolveValueUnionType<V>>uncheckedCast(value));
  }

  public static native <V extends @Nullable Object> Promise<V> resolve(
      @Nullable ResolveValueUnionType<V> value);

  @JsOverlay
  public static final <V extends @Nullable Object> Promise<V> resolve(@Nullable V value) {
    return resolve(Js.<ResolveValueUnionType<V>>uncheckedCast(value));
  }

  public Promise(PromiseExecutorCallbackFn<T> executor) {}

  @JsMethod(name = "catch")
  public native <V extends @Nullable Object> Promise<V> catch_(
      CatchOnRejectedCallbackFn<? extends V> onRejected);

  @Override
  public native <V extends @Nullable Object> Promise<V> then(
      @Nullable ThenOnFulfilledCallbackFn<? super T, ? extends V> onFulfilled,
      @Nullable ThenOnRejectedCallbackFn<? extends V> onRejected);

  @Override
  public native <V extends @Nullable Object> Promise<V> then(
      @Nullable ThenOnFulfilledCallbackFn<? super T, ? extends V> onFulfilled);

  @JsMethod(name = "finally")
  public native Promise<T> finally_(FinallyOnFinallyCallbackFn onFinally);
}
