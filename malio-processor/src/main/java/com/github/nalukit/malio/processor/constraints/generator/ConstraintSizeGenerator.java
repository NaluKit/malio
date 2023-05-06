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
package com.github.nalukit.malio.processor.constraints.generator;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.ProcessorException;
import com.github.nalukit.malio.processor.constraints.AbstractConstraint;
import com.github.nalukit.malio.processor.util.BuildWithMalioCommentProvider;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.Size;
import com.github.nalukit.malio.shared.internal.constraints.AbstractSizeConstraint;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ConstraintSizeGenerator
    extends AbstractGenerator {

  private AbstractConstraint<Size> constraint;

  private ConstraintSizeGenerator(Builder builder) {
    this.elements         = builder.elements;
    this.types            = builder.types;
    this.filer            = builder.filer;
    this.processorUtils   = builder.processorUtils;
    this.constraint = builder.constraint;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate(Element validatorElement, VariableElement variableElement)
      throws ProcessorException {
    TypeSpec.Builder typeSpec = createConstraintTypeSpec(validatorElement,
                                                         variableElement);

    int minSize = variableElement.getAnnotation(Size.class).min();
    int maxSize = variableElement.getAnnotation(Size.class).max();
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addModifiers(Modifier.PUBLIC)
                                 .addStatement("super($S, $S, $S, $L, $L)",
                                               this.processorUtils.getPackage(variableElement),
                                               this.processorUtils.setFirstCharacterToUpperCase(variableElement.getEnclosingElement()
                                                                                                               .getSimpleName()
                                                                                                               .toString()),
                                               variableElement.getSimpleName()
                                                              .toString(),
                                               minSize,
                                               maxSize
                                               )

                                 .build());

    super.writeFile(variableElement,
            constraint.getImplementationName(),
                    typeSpec);
  }

  private TypeSpec.Builder createConstraintTypeSpec(Element validatorElement,
                                                    VariableElement variableElement) {
    return TypeSpec.classBuilder(this.createConstraintClassName(validatorElement.getSimpleName()
                                                                                .toString(),
                                                                variableElement.getSimpleName()
                                                                               .toString(),
                    constraint.getImplementationName()))
                   .addJavadoc(BuildWithMalioCommentProvider.INSTANCE.getGeneratedComment())
                   .superclass(constraint.getValidationClass(variableElement))
                   .addModifiers(Modifier.PUBLIC,
                                 Modifier.FINAL);
  }

  public static class Builder {
    Elements        elements;
    Types           types;
    Filer           filer;
    ProcessorUtils  processorUtils;
    AbstractConstraint<Size> constraint;
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

    public Builder constraint(AbstractConstraint<Size> constraint) {
      this.constraint = constraint;
      return this;
    }

    public ConstraintSizeGenerator build() {
      return new ConstraintSizeGenerator(this);
    }
  }
}
