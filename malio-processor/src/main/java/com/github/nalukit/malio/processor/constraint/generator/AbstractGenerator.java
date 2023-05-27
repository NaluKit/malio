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
package com.github.nalukit.malio.processor.constraint.generator;

import com.github.nalukit.malio.processor.ProcessorException;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.squareup.javapoet.CodeBlock;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class AbstractGenerator
    implements IsGenerator {

  protected Elements       elements;
  protected Types          types;
  protected Filer          filer;
  protected ProcessorUtils processorUtils;
  protected boolean        generateCheckMethod;
  protected boolean        generateValidateMethod;

  public AbstractGenerator() {
  }

  protected abstract CodeBlock generate(Element clazz,
                                        VariableElement field,
                                        String suffix);

  @Override
  public final CodeBlock generateCheckArray(Element clazz,
                                            VariableElement field)
      throws ProcessorException {
    return generate(clazz,
                    field,
                    ".check(bean.$L()[i])");
  }

  @Override
  public final CodeBlock generateValidArray(Element clazz,
                                            VariableElement field)
      throws ProcessorException {
    return generate(clazz,
                    field,
                    ".isValid(bean.$L()[i], validationResult)");
  }

  @Override
  public final CodeBlock generateCheckCollection(Element clazz,
                                                 VariableElement field)
      throws ProcessorException {
    return generate(clazz,
                    field,
                    ".check(bean.$L().get(i))");
  }

  @Override
  public final CodeBlock generateValidCollection(Element clazz,
                                                 VariableElement field)
      throws ProcessorException {
    return generate(clazz,
                    field,
                    ".isValid(bean.$L().get(i), validationResult)");
  }

  @Override
  public final CodeBlock generateCheckNative(Element clazz,
                                             VariableElement field)
      throws ProcessorException {
    return generate(clazz,
                    field,
                    ".check(bean.$L())");
  }

  @Override
  public final CodeBlock generateValidNative(Element clazz,
                                             VariableElement field)
      throws ProcessorException {
    return generate(clazz,
                    field,
                    ".isValid(bean.$L(), validationResult)");
  }
}
