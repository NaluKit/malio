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

import io.github.nalukit.malio.processor.constraint.AbstractProcessorConstraint;
import io.github.nalukit.malio.processor.util.ProcessorUtils;
import io.github.nalukit.malio.shared.annotation.field.DecimalMax;
import com.squareup.javapoet.CodeBlock;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ConstraintMaxDecimalGenerator
    extends AbstractGenerator {

  private final AbstractProcessorConstraint<DecimalMax> constraint;

  private ConstraintMaxDecimalGenerator(Builder builder) {
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
    return CodeBlock.builder()
                    .add("new $T($S, $S, $S, $S, $S)" + suffix,
                         constraint.getValidationClass(field),
                         this.processorUtils.getPackage(field),
                         this.processorUtils.setFirstCharacterToUpperCase(field.getEnclosingElement()
                                                                               .getSimpleName()
                                                                               .toString()),
                         field.getSimpleName()
                              .toString(),
                         field.getAnnotation(DecimalMax.class)
                              .value(),
                         field.getAnnotation(DecimalMax.class)
                              .message(),
                         this.processorUtils.createGetMethodName(field.getSimpleName()
                                                                      .toString()))
                    .build();
  }

  public static class Builder {

    Elements       elements;
    Types          types;
    Filer          filer;
    ProcessorUtils processorUtils;

    AbstractProcessorConstraint<DecimalMax> constraint;

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

    public Builder constraint(AbstractProcessorConstraint<DecimalMax> constraint) {
      this.constraint = constraint;
      return this;
    }

    public ConstraintMaxDecimalGenerator build() {
      return new ConstraintMaxDecimalGenerator(this);
    }
  }
}
