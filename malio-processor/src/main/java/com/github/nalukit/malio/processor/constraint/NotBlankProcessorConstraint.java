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
package com.github.nalukit.malio.processor.constraint;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.constraint.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.constraint.generator.ConstraintNotBlankGenerator;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.shared.annotation.field.NotBlank;
import com.github.nalukit.malio.shared.internal.constraint.NotBlankConstraint;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import java.util.Collections;
import java.util.List;

public class NotBlankProcessorConstraint
    extends AbstractProcessorConstraint<NotBlank> {

  public NotBlankProcessorConstraint() {
  }

  @Override
  public Class<NotBlank> annotationType() {
    return NotBlank.class;
  }

  @Override
  public String getImplementationName() {
    return Constants.MALIO_CONSTRAINT_NOTBLANK_IMPL_NAME;
  }

  @Override
  public ConstraintType getConstraintType() {
    return ConstraintType.NOT_BLANK_CONSTRAINT;
  }

  @Override
  public TypeName getValidationClass(VariableElement variableElement) {
    return ClassName.get(NotBlankConstraint.class);
  }

  @Override
  protected List<TypeKind> getSupportedPrimitives() {
    return null;
  }

  @Override
  protected List<Class<?>> getSupportedDeclaredType() {
    return Collections.singletonList(String.class);
  }

  @Override
  protected AbstractGenerator createGenerator() {
    return ConstraintNotBlankGenerator.builder()
                                      .elements(this.processingEnvironment.getElementUtils())
                                      .filer(this.processingEnvironment.getFiler())
                                      .types(this.processingEnvironment.getTypeUtils())
                                      .processorUtils(this.processorUtils)
                                      .constraint(this)
                                      .build();
  }

  @Override
  public boolean isTargetingNative() {
    return true;
  }

  @Override
  public boolean isTargetingArrayItem() {
    return false;
  }

  @Override
  public boolean isTargetingListItem() {
    return false;
  }

}
