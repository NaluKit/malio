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
import com.github.nalukit.malio.processor.constraints.generator.ConstraintMaxDecimalGenerator;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.DecimalMax;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class MaxDecimalConstraint
    extends AbstractConstraint<DecimalMax> {

  public MaxDecimalConstraint(ProcessingEnvironment processingEnv,
                              ProcessorUtils processorUtils) {
    super(processingEnv,
          processorUtils);
  }

  @Override
  public Class<DecimalMax> annotationType() {
    return DecimalMax.class;
  }

  @Override
  public String getImplementationName() {
    return Constants.MALIO_CONSTRAINT_MAXDECIMALVALUE_IMPL_NAME;
  }

  @Override
  public ConstraintType getConstraintType() {
    return ConstraintType.MAX_DECIMAL_VALUE_CONSTRAINT;
  }

  @Override
  public TypeName getValidationClass(VariableElement variableElement) {
    return ClassName.get(com.github.nalukit.malio.shared.internal.constraints.MaxDecimalValueConstraint.class);
  }

  @Override
  protected List<TypeKind> getSupportedPrimitives() {
    return null;
  }

  @Override
  protected List<Class<?>> getSupportedDeclaredType() {
    return Collections.singletonList(BigDecimal.class);
  }

  @Override
  protected AbstractGenerator createGenerator() {
    return ConstraintMaxDecimalGenerator.builder()
                                             .elements(this.processingEnvironment.getElementUtils())
                                             .filer(this.processingEnvironment.getFiler())
                                             .types(this.processingEnvironment.getTypeUtils())
                                             .processorUtils(this.processorUtils)
                                             .constraint(this)
                                             .build();
  }

}
