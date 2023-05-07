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
package com.github.nalukit.malio.processor.constraints;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.constraints.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.constraints.generator.ConstraintMinValueGenerator;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.MinValue;
import com.github.nalukit.malio.shared.internal.constraints.AbstractMinValueConstraint;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import java.util.Arrays;
import java.util.List;

public class MinValueConstraint
    extends AbstractConstraint<MinValue> {

  public MinValueConstraint(ProcessingEnvironment processingEnv,
                            ProcessorUtils processorUtils) {
    super(processingEnv,
          processorUtils);
  }

  @Override
  public Class<MinValue> annotationType() {
    return MinValue.class;
  }

  @Override
  public String getImplementationName() {
    return Constants.MALIO_CONSTRAINT_MINVALUE_IMPL_NAME;
  }

  @Override
  public ConstraintType getConstraintType() {
    return ConstraintType.MIN_VALUE_CONSTRAINT;
  }

  @Override
  public TypeName getValidationClass(VariableElement variableElement) {
    return ClassName.get(AbstractMinValueConstraint.class);
  }

  @Override
  protected List<TypeKind> getSupportedPrimitives() {
    return Arrays.asList(TypeKind.INT,
                         TypeKind.LONG);
  }

  @Override
  protected List<Class<?>> getSupportedDeclaredType() {
    return Arrays.asList(Integer.class,
                         Long.class);
  }

  @Override
  protected AbstractGenerator createGenerator() {
    return ConstraintMinValueGenerator.builder()
                                      .elements(this.processingEnvironment.getElementUtils())
                                      .filer(this.processingEnvironment.getFiler())
                                      .types(this.processingEnvironment.getTypeUtils())
                                      .processorUtils(this.processorUtils)
                                      .constraint(this)
                                      .build();
  }

}
