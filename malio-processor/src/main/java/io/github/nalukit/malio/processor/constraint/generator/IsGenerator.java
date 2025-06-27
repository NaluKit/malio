/*
 * Copyright © 2023 Frank Hossfeld, Philipp Kohl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.nalukit.malio.processor.constraint.generator;

import io.github.nalukit.malio.processor.ProcessorException;
import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;

public interface IsGenerator {

  CodeBlock generateCheckArray(Element clazz,
                               VariableElement field)
      throws ProcessorException;

  CodeBlock generateValidArray(Element clazz,
                               VariableElement field)
      throws ProcessorException;

  CodeBlock generateCheckCollection(Element clazz,
                                    VariableElement field)
      throws ProcessorException;

  CodeBlock generateValidCollection(Element clazz,
                                    VariableElement field)
      throws ProcessorException;

  CodeBlock generateCheckNative(Element clazz,
                                VariableElement field)
      throws ProcessorException;

  CodeBlock generateValidNative(Element clazz,
                                VariableElement field)
      throws ProcessorException;
}
