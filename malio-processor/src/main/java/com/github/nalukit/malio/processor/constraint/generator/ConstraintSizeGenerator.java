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

import com.github.nalukit.malio.processor.constraint.AbstractProcessorConstraint;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.Size;
import com.squareup.javapoet.CodeBlock;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ConstraintSizeGenerator
    extends AbstractGenerator {

  private AbstractProcessorConstraint<Size> constraint;

  private ConstraintSizeGenerator(Builder builder) {
    this.elements       = builder.elements;
    this.types          = builder.types;
    this.filer          = builder.filer;
    this.processorUtils = builder.processorUtils;
    this.constraint     = builder.constraint;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  protected CodeBlock generate(Element clazz,
                               VariableElement field,
                               String suffix) {
    int minSize = field.getAnnotation(Size.class)
                       .min();
    int maxSize = field.getAnnotation(Size.class)
                       .max();
    return CodeBlock.builder()
                    .add("new $T($S, $S, $S, $L, $L, $S)" + suffix,
                         constraint.getValidationClass(field),
                         this.processorUtils.getPackage(field),
                         this.processorUtils.setFirstCharacterToUpperCase(field.getEnclosingElement()
                                                                               .getSimpleName()
                                                                               .toString()),
                         field.getSimpleName()
                              .toString(),
                         minSize,
                         maxSize,
                         field.getAnnotation(constraint.annotationType())
                              .message(),
                         this.processorUtils.createGetMethodName(field.getSimpleName()
                                                                      .toString()))
                    .build();
  }

  public static class Builder {
    Elements                          elements;
    Types                             types;
    Filer                             filer;
    ProcessorUtils                    processorUtils;
    AbstractProcessorConstraint<Size> constraint;

    public Builder elements(Elements elements) {
      this.elements = elements;
      return this;
    }

    public Builder types(Types types) {
      this.types = types;
      return this;
    }

    public Builder filer(Filer filer) {
      this.filer = filer;
      return this;
    }

    public Builder processorUtils(ProcessorUtils processorUtils) {
      this.processorUtils = processorUtils;
      return this;
    }

    public Builder constraint(AbstractProcessorConstraint<Size> constraint) {
      this.constraint = constraint;
      return this;
    }

    public ConstraintSizeGenerator build() {
      return new ConstraintSizeGenerator(this);
    }
  }
}
