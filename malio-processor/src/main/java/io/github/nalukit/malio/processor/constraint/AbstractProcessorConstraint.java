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
package io.github.nalukit.malio.processor.constraint;

import io.github.nalukit.malio.processor.ProcessorException;
import io.github.nalukit.malio.processor.constraint.generator.AbstractGenerator;
import io.github.nalukit.malio.processor.constraint.generator.IsGenerator;
import io.github.nalukit.malio.processor.exception.UnsupportedTypeException;
import io.github.nalukit.malio.processor.model.ConstraintModel;
import io.github.nalukit.malio.processor.model.ConstraintType;
import io.github.nalukit.malio.processor.util.ProcessorUtils;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import java.lang.annotation.Annotation;
import java.util.List;

public abstract class AbstractProcessorConstraint<T extends Annotation>
    implements IsGenerator {

  protected ProcessingEnvironment processingEnvironment;
  protected ProcessorUtils        processorUtils;

  public AbstractProcessorConstraint() {
  }

  public ConstraintModel createConstraintModel(Element varType,
                                               Element varName) {
    return new ConstraintModel(this.processorUtils.getPackageAsString(varName),
                               varType.getSimpleName()
                                      .toString(),
                               varName.getSimpleName()
                                      .toString(),
                               getImplementationName(),
                               getConstraintType());
  }

  public AbstractProcessorConstraint<T> setUp(ProcessingEnvironment processingEnv,
                                              ProcessorUtils processorUtils) {
    this.processingEnvironment = processingEnv;
    this.processorUtils        = processorUtils;
    return this;
  }

  public void checkDataType(VariableElement variableElement) {
    if (getSupportedDeclaredType() == null && getSupportedPrimitives() == null) {
      return;
    }

    if (!processorUtils.checkDataType(variableElement,
                                      getSupportedPrimitives(),
                                      getSupportedDeclaredType())) {
      throw new UnsupportedTypeException("Class >>" + variableElement.getEnclosingElement() + "<< - Type >>" + variableElement.asType() + "<< not supported for >>" + getClass().getSimpleName() + "<<");
    }
  }

  public void checkDataTypeArrayItem(VariableElement variableElement) {
    if (getSupportedDeclaredType() == null && getSupportedPrimitives() == null) {
      return;
    }

    if (!processorUtils.checkDataTypeArrayItem(variableElement,
                                               getSupportedPrimitives(),
                                               getSupportedDeclaredType())) {
      throw new UnsupportedTypeException("Class >>" + variableElement.getEnclosingElement() + "<< - Type >>" + variableElement.asType() + "<< not supported for >>" + getClass().getSimpleName() + "<<");
    }
  }

  public void checkDataTypeCollectionItem(VariableElement variableElement) {
    if (getSupportedDeclaredType() == null && getSupportedPrimitives() == null) {
      return;
    }

    if (!processorUtils.checkDataTypeCollectionItem(variableElement,
                                                    getSupportedPrimitives(),
                                                    getSupportedDeclaredType())) {
      throw new UnsupportedTypeException("Class >>" + variableElement.getEnclosingElement() + "<< - Type >>" + variableElement.asType() + "<< not supported for >>" + getClass().getSimpleName() + "<<");
    }
  }

  public abstract Class<T> annotationType();

  public List<Element> getVarsWithAnnotation(TypeElement element) {
    return this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnvironment,
                                                                        element,
                                                                        this.annotationType());
  }

  public abstract String getImplementationName();

  public abstract ConstraintType getConstraintType();

  public abstract TypeName getValidationClass(VariableElement variableElement);

  protected abstract List<TypeKind> getSupportedPrimitives();

  protected abstract List<Class<?>> getSupportedDeclaredType();

  protected abstract AbstractGenerator createGenerator();

  public abstract boolean isTargetingNative();

  public abstract boolean isTargetingArrayItem();

  public abstract boolean isTargetingListItem();

  @Override
  public CodeBlock generateCheckArray(Element clazz,
                                      VariableElement field)
      throws ProcessorException {
    return this.createGenerator()
               .generateCheckArray(clazz,
                                   field);
  }

  @Override
  public CodeBlock generateValidArray(Element clazz,
                                      VariableElement field)
      throws ProcessorException {
    return this.createGenerator()
               .generateValidArray(clazz,
                                   field);
  }

  @Override
  public CodeBlock generateCheckCollection(Element clazz,
                                           VariableElement field)
      throws ProcessorException {
    return this.createGenerator()
               .generateCheckCollection(clazz,
                                        field);
  }

  @Override
  public CodeBlock generateValidCollection(Element clazz,
                                           VariableElement field)
      throws ProcessorException {
    return this.createGenerator()
               .generateValidCollection(clazz,
                                        field);
  }

  @Override
  public CodeBlock generateCheckNative(Element clazz,
                                       VariableElement field)
      throws ProcessorException {
    return this.createGenerator()
               .generateCheckNative(clazz,
                                    field);
  }

  @Override
  public CodeBlock generateValidNative(Element clazz,
                                       VariableElement field)
      throws ProcessorException {
    return this.createGenerator()
               .generateValidNative(clazz,
                                    field);
  }
}
