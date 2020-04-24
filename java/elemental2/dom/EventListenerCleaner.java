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
package elemental2.dom;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.MoreCollectors.onlyElement;

import jsinterop.generator.model.AbstractRewriter;
import jsinterop.generator.model.AbstractVisitor;
import jsinterop.generator.model.Method;
import jsinterop.generator.model.ModelVisitor;
import jsinterop.generator.model.Parameter;
import jsinterop.generator.model.Program;
import jsinterop.generator.model.Type;
import jsinterop.generator.model.TypeReference;
import jsinterop.generator.model.UnionTypeReference;

/** Remove ambiguous addEventListener/removeEventListener EventTarget api. */
public class EventListenerCleaner implements ModelVisitor {
  private static final String EVENT_TARGET_FQN = "elemental2.dom.EventTarget";

  @Override
  public void applyTo(Program program) {
    program.accept(
        new AbstractVisitor() {
          @Override
          public void exitType(Type type) {
            if (implementsEventTarget(type)) {
              type.getMethods().stream()
                  .filter(EventListenerCleaner::isAddOrRemoveEventListenerMethods)
                  .forEach(EventListenerCleaner::cleanEventListenerMethod);
            }
          }
        });
  }

  /** Returns true if the type is the EventTarget type or if the type implements EventTarget. */
  private static boolean implementsEventTarget(Type type) {
    return EVENT_TARGET_FQN.equals(type.getJavaFqn())
        || type.getImplementedTypes().stream()
            .anyMatch(t -> EVENT_TARGET_FQN.equals(t.getJavaTypeFqn()));
  }

  private static void cleanEventListenerMethod(Method method) {
    checkState(method.getParameters().size() >= 2);

    Parameter listenerParameter = method.getParameters().get(1);

    method.accept(
        new AbstractRewriter() {
          @Override
          public Parameter rewriteParameter(Parameter node) {
            if (node != listenerParameter) {
              return node;
            }

            checkState(node.getType() instanceof UnionTypeReference);
            UnionTypeReference parameterType = (UnionTypeReference) node.getType();

            TypeReference eventListenerType =
                parameterType.getTypes().stream()
                    .filter(t -> "elemental2.dom.EventListener".equals(t.getJavaTypeFqn()))
                    .collect(onlyElement());

            Parameter newParameter = Parameter.from(node);
            newParameter.setType(eventListenerType);

            return newParameter;
          }
        });
  }

  private static boolean isAddOrRemoveEventListenerMethods(Method m) {
    return "addEventListener".equals(m.getName()) || "removeEventListener".equals(m.getName());
  }
}
