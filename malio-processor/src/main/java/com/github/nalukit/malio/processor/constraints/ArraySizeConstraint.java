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
import com.github.nalukit.malio.processor.constraints.generator.ConstraintArraySizeGenerator;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.ArraySize;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArraySizeConstraint
    extends AbstractConstraint<ArraySize> {

  public ArraySizeConstraint(ProcessingEnvironment processingEnv,
                             ProcessorUtils processorUtils) {
    super(processingEnv,
          processorUtils);
  }

  @Override
  public Class<ArraySize> annotationType() {
    return ArraySize.class;
  }

  @Override
  public String getImplementationName() {
    return Constants.MALIO_CONSTRAINT_ARRAYSIZE_IMPL_NAME;
  }

  @Override
  public ConstraintType getConstraintType() {
    return ConstraintType.ARRAY_SIZE_CONSTRAINT;
  }

  @Override
  public TypeName getValidationClass(VariableElement variableElement) {
    return ClassName.get(com.github.nalukit.malio.shared.internal.constraints.ArraySizeConstraint.class);
  }

  @Override
  // we miss the array type here and use only the primitive types, which will be used to check the array type.
  protected List<TypeKind> getSupportedPrimitives() {
    return Arrays.asList(TypeKind.INT, TypeKind.LONG, TypeKind.SHORT, TypeKind.BOOLEAN, TypeKind.BYTE,
            TypeKind.FLOAT, TypeKind.DOUBLE, TypeKind.CHAR);
  }

  @Override
  protected List<Class<?>> getSupportedDeclaredType() {
    return Collections.singletonList(Object[].class);
  }

  @Override
  protected AbstractGenerator createGenerator() {
    return ConstraintArraySizeGenerator.builder()
                                  .elements(this.processingEnvironment.getElementUtils())
                                  .filer(this.processingEnvironment.getFiler())
                                  .types(this.processingEnvironment.getTypeUtils())
                                  .processorUtils(this.processorUtils)
                                  .constraint(this)
                                  .build();
  }

}
