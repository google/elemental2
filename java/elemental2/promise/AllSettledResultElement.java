package elemental2.promise;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Represents an element of the array returned by Promise.allSettled().
 * @param <VALUE> the type of the succeeded promise.
 */
@JsType(isNative = true)
public interface AllSettledResultElement<VALUE> {
    @JsProperty
    String getStatus();

    @JsProperty
    VALUE getValue();

    @JsProperty
    Object getReason();
}
