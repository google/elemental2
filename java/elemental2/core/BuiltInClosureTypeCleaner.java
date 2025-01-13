/*
 * Copyright 2016 Google Inc.
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
 */
package elemental2.core;

import static com.google.common.base.Preconditions.checkState;
import static java.util.stream.Collectors.toCollection;
import static jsinterop.generator.model.PredefinedTypes.ARRAY_STAMPER;
import static jsinterop.generator.model.PredefinedTypes.JS;
import static jsinterop.generator.model.PredefinedTypes.OBJECT;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.MoreCollectors;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import jsinterop.generator.helper.ModelHelper;
import jsinterop.generator.model.AbstractRewriter;
import jsinterop.generator.model.AbstractVisitor;
import jsinterop.generator.model.Annotation;
import jsinterop.generator.model.AnnotationType;
import jsinterop.generator.model.ArrayTypeReference;
import jsinterop.generator.model.Entity;
import jsinterop.generator.model.Field;
import jsinterop.generator.model.JavaTypeReference;
import jsinterop.generator.model.LiteralExpression;
import jsinterop.generator.model.Method;
import jsinterop.generator.model.MethodInvocation;
import jsinterop.generator.model.ModelVisitor;
import jsinterop.generator.model.Parameter;
import jsinterop.generator.model.ParametrizedTypeReference;
import jsinterop.generator.model.Program;
import jsinterop.generator.model.ReturnStatement;
import jsinterop.generator.model.Type;
import jsinterop.generator.model.TypeQualifier;
import jsinterop.generator.model.TypeReference;
import jsinterop.generator.model.TypeVariableReference;

/**
 * Do some cleaning tasks around built-in Array and Object types:
 *
 * <ul>
 *   <li>Improve Array constructor, concat and unshift method definition to be type safer
 *   <li>Add helper methods on JsArray in order to ease the conversion from and to java array.
 *   <li>Remove built-in type parameters for Object type.
 *   <li>Remove <code>globalThis</code> global variable. This variable is typed with <code>Global
 *       </code> type that are not expressed in the externs file. <code>Global</code> is a built-in
 *       JsCompiler type.
 * </ul>
 */
public class BuiltInClosureTypeCleaner implements ModelVisitor {
  private static final ImmutableSet<String> TYPED_ARRAY_TYPES =
      ImmutableSet.of(
          "ArrayBufferView",
          "TypedArray",
          "Int8Array",
          "Uint8Array",
          "Uint8ClampedArray",
          "Int16Array",
          "Uint16Array",
          "Int32Array",
          "Uint32Array",
          "Float32Array",
          "Float64Array",
          "BigInt64Array",
          "BigUint64Array");

  @Override
  public void applyTo(Program program) {
    program.accept(
        new AbstractVisitor() {
          @Override
          public void exitType(Type type) {
            String nativeFqn = type.getNativeFqn();
            if (Objects.equals(nativeFqn, "ReadonlyMap") || Objects.equals(nativeFqn, "Map")) {
              cleanCommonMapMethods(type);
            } else if (Objects.equals(nativeFqn, "ReadonlyArray")) {
              cleanCommonArrayMethods(type);
              // Remove the length field. The field will be converted to a getLength method and will
              // conflict with the JsOverlay method defined on JsArrayLike.
              type.removeField(
                  type.getFields().stream()
                      .filter(f -> "length".equals(f.getName()))
                      .collect(MoreCollectors.onlyElement()));
            } else if (Objects.equals(nativeFqn, "Array")) {
              cleanArrayType(type);
              addJavaArrayHelperMethods(type);
            } else if (Objects.equals(nativeFqn, "Object")) {
              // JsCompiler uses a hardcoded definition for the Object type, one with two type
              // parameters (from IObject). That makes the resulting java type to be generated as
              // the following parametrized type:
              //    class JsObject<IObject#KEY1, IObject#VALUE> {}
              // Since Object in elemental should not be a parameterized type, the type parameters
              // are cleared here.
              Collection<TypeReference> typeParameters = type.getTypeParameters();

              checkState(
                  typeParameters.size() == 2, "Object is not defined with two type parameters");

              type.getTypeParameters().clear();
            }
          }
        });

    // To mirror the type definitions in TypeScript, Typed Array in closure are parametrized with a
    // type parameter that is unused. To avoid parameterization in our generated Java code, we will
    // simply remove the type parameter.
    program.accept(
        new AbstractRewriter() {
          @Override
          public boolean shouldProcessType(Type type) {
            return TYPED_ARRAY_TYPES.contains(type.getNativeFqn());
          }

          @Override
          public Entity rewriteType(Type type) {
            if (TYPED_ARRAY_TYPES.contains(type.getNativeFqn())) {
              Collection<TypeReference> typeParameters = type.getTypeParameters();

              checkState(
                  typeParameters.isEmpty()
                      || (typeParameters.size() == 1
                          && typeParameters
                              .iterator()
                              .next()
                              .getTypeName()
                              .equals("TArrayBuffer")));

              type.getTypeParameters().clear();
            }
            return type;
          }

          @Override
          public TypeReference rewriteParametrizedTypeReference(
              ParametrizedTypeReference typeReference) {
            if (TYPED_ARRAY_TYPES.contains(
                typeReference.getMainType().getJsDocAnnotationString())) {
              // Some typed array instance functions are defined as returning the special `THIS`
              // template type. That will create some type reference to the enclosing type that are
              // automatically parametrized with the type parameter we removed above.
              List<TypeReference> typeArgument = typeReference.getActualTypeArguments();
              checkState(
                  typeArgument.size() == 1
                      && typeArgument.get(0).getTypeName().equals("TArrayBuffer"));
              return typeReference.getMainType();
            }
            return typeReference;
          }
        });

    program.accept(
        new AbstractRewriter() {
          @Override
          public boolean shouldProcessType(Type node) {
            return ModelHelper.isGlobalType(node);
          }

          @Override
          public Entity rewriteField(Field field) {
            // Remove "globalThis" field from the global type.
            return "globalThis".equals(field.getName()) ? null : field;
          }
        });
  }

  private static void addJavaArrayHelperMethods(Type jsArrayType) {
    checkState(jsArrayType.getTypeParameters().size() == 1);
    TypeReference arrayTypeParameter = jsArrayType.getTypeParameters().stream().findFirst().get();

    // Add {@code T[] asArray(T[] reference)} method to convert correctly JsArray to java array.
    // This method stamps correctly the java array.
    Method asArray = new Method();
    asArray.addAnnotation(Annotation.builder().type(AnnotationType.JS_OVERLAY).build());
    asArray.setFinal(true);
    asArray.setName("asArray");
    asArray.setReturnType(new ArrayTypeReference(arrayTypeParameter));
    asArray.addParameter(
        Parameter.builder()
            .setName("reference")
            .setType(new ArrayTypeReference(arrayTypeParameter))
            .build());
    asArray.setBody(
        new ReturnStatement(
            MethodInvocation.builder()
                .setInvocationTarget(new TypeQualifier(ARRAY_STAMPER.getReference(false)))
                .setMethodName("stampJavaTypeInfo")
                .setArgumentTypes(
                    OBJECT.getReference(false), new ArrayTypeReference(arrayTypeParameter))
                .setArguments(new LiteralExpression("this"), new LiteralExpression("reference"))
                .build()));
    jsArrayType.addMethod(asArray);

    // Add {@code static JsArray<T> from(T[])} method to convert java array to JsArray.
    TypeVariableReference methodTypeVariable = new TypeVariableReference("T", null);
    Method from = new Method();
    from.addAnnotation(Annotation.builder().type(AnnotationType.JS_OVERLAY).build());
    from.setStatic(true);
    from.setFinal(true);
    from.setTypeParameters(ImmutableList.of(methodTypeVariable));
    from.setName("asJsArray");
    from.setReturnType(
        new ParametrizedTypeReference(
            new JavaTypeReference(jsArrayType), ImmutableList.of(methodTypeVariable)));
    from.addParameter(
        Parameter.builder()
            .setName("array")
            .setType(new ArrayTypeReference(methodTypeVariable))
            .build());
    from.setBody(
        new ReturnStatement(
            MethodInvocation.builder()
                .setInvocationTarget(new TypeQualifier(JS.getReference(false)))
                .setMethodName("uncheckedCast")
                .setArgumentTypes(OBJECT.getReference(false))
                .setArguments(new LiteralExpression("array"))
                .build()));
    jsArrayType.addMethod(from);
  }

  private static void cleanArrayType(Type arrayType) {
    TypeReference arrayValueTypeParameter = getArrayValueTypeParameter(arrayType);

    // 1. Improve the typing of the Array constructor. Array constructor should be parameterized by
    // T (not Object).
    checkState(arrayType.getConstructors().size() == 1);
    Method arrayConstructor = arrayType.getConstructors().get(0);
    improveArrayMethodTyping(arrayConstructor, arrayValueTypeParameter);

    // 2. Improve the typing of Array.unshift. It must accept items of type T (not Object).
    Optional<Method> unshiftMethod =
        arrayType.getMethods().stream().filter(m -> "unshift".equals(m.getName())).findAny();
    checkState(unshiftMethod.isPresent());
    improveArrayMethodTyping(unshiftMethod.get(), arrayValueTypeParameter);

    // 3. Clean methods defined on Array and ReadonlyArray.
    cleanCommonArrayMethods(arrayType);
  }

  // TODO(dramaix): Add a logic to detect method that have a callback with an array as last
  //  parameter.
  private static final ImmutableList<String> ARRAY_METHODS_WITH_CLEANABLE_CALLBACKS =
      ImmutableList.of("filter", "forEach", "reduce", "map", "reduceRight", "every", "some");

  private static final ImmutableList<String> MAP_METHODS_WITH_CLEANABLE_CALLBACKS =
      ImmutableList.of("forEach");

  private static final ImmutableSet<String> METHODS_WITH_SINGULAR_VALUE_IN_FIRST_PARAMETER =
      ImmutableSet.of("indexOf", "lastIndexOf", "includes");

  private static void cleanCommonArrayMethods(Type arrayType) {
    // 1. Change concat() method to accept items of type T and not Object and return an array of T.
    cleanArrayConcatMethod(arrayType);

    // 2. Fix some callback definition to remove the last parameter that represent the array itself.
    // This parameter causes inheritance issue between ReadOnlyArray and Array as the type of this
    // parameter is redefined to match the enclosing type. Java developers can easily capture the
    // array the method is applied on and do not need this parameter.
    cleanCallbacks(
        arrayType,
        ARRAY_METHODS_WITH_CLEANABLE_CALLBACKS.stream()
            .map(m -> getCallBackParameterType(m, arrayType))
            .collect(toCollection(HashSet::new)));

    // Ensure that indexOf-like methods accept a typed value in their first argument, which is not
    // the case for ReadonlyArray.
    for (Method method : arrayType.getMethods()) {
      if (METHODS_WITH_SINGULAR_VALUE_IN_FIRST_PARAMETER.contains(method.getName())) {
        method.getParameters().get(0).setType(getArrayValueTypeParameter(arrayType));
      }
    }
  }

  private static void cleanCommonMapMethods(Type mapType) {
    // Fix some callback definition to remove the last parameter that represent the map itself.
    //
    // This parameter causes inheritance issue between ReadonlyMap and Map as the type of this
    // parameter is redefined to match the enclosing type. Java developers can easily capture the
    // array the method is applied on and do not need this parameter.
    cleanCallbacks(
        mapType,
        MAP_METHODS_WITH_CLEANABLE_CALLBACKS.stream()
            .map(m -> getCallBackParameterType(m, mapType))
            .collect(toCollection(HashSet::new)));
  }

  private static void cleanCallbacks(Type type, Set<String> cleanableCallbackTypes) {
    for (Type callbackType : type.getInnerTypes()) {
      if (cleanableCallbackTypes.remove(callbackType.getJavaFqn())) {
        Method callbackMethod = callbackType.getMethods().get(0);
        checkState(
            Iterables.getLast(callbackMethod.getParameters())
                .getType()
                .getJavaTypeFqn()
                .equals(type.getJavaFqn()),
            "Invalid callback found %s",
            callbackType.getJavaFqn());

        checkState(
            callbackType.isInterface() && callbackType.getMethods().size() == 1,
            "Invalid callback found %s",
            callbackType.getJavaFqn());

        callbackMethod.getParameters().remove(callbackMethod.getParameters().size() - 1);

        // clean the native fqn (closure js doc) of the callback because this will be used later in
        // DuplicatedTypesUnifier to identify the callback.
        String callbackNativeFqn = callbackType.getNativeFqn();
        checkState(callbackNativeFqn != null && callbackNativeFqn.startsWith("function("));
        callbackType.setNativeFqn(
            callbackNativeFqn.substring(0, callbackNativeFqn.lastIndexOf(','))
                + callbackNativeFqn.substring(callbackNativeFqn.indexOf(')')));
      }
    }

    checkState(
        cleanableCallbackTypes.isEmpty(),
        "The following callbacks %s are not been found.",
        cleanableCallbackTypes);
  }

  private static String getCallBackParameterType(String methodName, Type enclosingType) {
    Optional<Method> methodOptional =
        enclosingType.getMethods().stream().filter(m -> methodName.equals(m.getName())).findAny();
    checkState(methodOptional.isPresent());
    Method methodWithCallback = methodOptional.get();

    for (Parameter p : methodWithCallback.getParameters()) {
      if ("callback".equals(p.getName())) {
        return p.getType().getJavaTypeFqn();
      }
    }

    throw new IllegalStateException(
        "Callback parameter not found on " + enclosingType.getName() + "." + methodName);
  }
  /**
   * Improve the typing of Array.concat. It must accept items of type T (not Object) and return an
   * array of T.
   */
  private static void cleanArrayConcatMethod(Type arrayType) {
    TypeReference arrayValueTypeParameter = getArrayValueTypeParameter(arrayType);

    Optional<Method> concatMethodOptional =
        arrayType.getMethods().stream().filter(m -> "concat".equals(m.getName())).findAny();
    checkState(concatMethodOptional.isPresent());
    Method concatMethod = concatMethodOptional.get();

    improveArrayMethodTyping(concatMethod, arrayValueTypeParameter);

    checkState(concatMethod.getReturnType() instanceof ParametrizedTypeReference);
    ParametrizedTypeReference concatReturnType =
        (ParametrizedTypeReference) concatMethod.getReturnType();
    checkState(
        concatReturnType.getActualTypeArguments().size() == 1
            && concatReturnType.getActualTypeArguments().get(0).isReferenceTo(OBJECT));
    concatMethod.setReturnType(
        new ParametrizedTypeReference(
            concatReturnType.getMainType(), ImmutableList.of(arrayValueTypeParameter)));
  }

  /**
   * Improves the typing of Array methods that should accept items of type T (the type parameter of
   * the Array) instead of Object.
   */
  private static void improveArrayMethodTyping(Method m, TypeReference arrayTypeParameter) {
    checkState(m.getParameters().size() == 1);
    Parameter firstParameter = m.getParameters().get(0);
    checkState(firstParameter.getType().isReferenceTo(OBJECT));
    m.getParameters()
        .set(0, firstParameter.toBuilder().setName("items").setType(arrayTypeParameter).build());
  }

  private static TypeReference getArrayValueTypeParameter(Type arrayType) {
    Collection<TypeReference> typeParameters = arrayType.getTypeParameters();
    checkState(typeParameters.size() == 1, "Unexpected array definitions from JsCompiler");
    return typeParameters.iterator().next();
  }
}
