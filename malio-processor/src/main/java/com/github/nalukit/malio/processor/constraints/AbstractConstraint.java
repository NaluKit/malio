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

import com.github.nalukit.malio.processor.ProcessorException;
import com.github.nalukit.malio.processor.constraints.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.constraints.generator.IsGenerator;
import com.github.nalukit.malio.processor.exceptions.UnsupportedTypeException;
import com.github.nalukit.malio.processor.model.ConstraintModel;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.model.ValidatorModel;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import java.lang.annotation.Annotation;
import java.util.List;

public abstract class AbstractConstraint<T extends Annotation>
    implements IsGenerator {

  protected final ProcessingEnvironment processingEnvironment;
  protected final ProcessorUtils        processorUtils;

  public AbstractConstraint(ProcessingEnvironment processingEnv,
                            ProcessorUtils processorUtils) {
    this.processingEnvironment = processingEnv;
    this.processorUtils        = processorUtils;
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

  public void checkDataType(VariableElement variableElement,
                            ValidatorModel.Type type,
                            Target targetForCollectionAndList) {
    if (getSupportedDeclaredType() == null && getSupportedPrimitives() == null) {
      return;
    }

    if (!processorUtils.checkDataType(variableElement,
                                      type,
                                      targetForCollectionAndList,
                                      getSupportedPrimitives(),
                                      getSupportedDeclaredType())) {
      throw new UnsupportedTypeException("Class >>" +
                                         variableElement.getEnclosingElement() +
                                         "<< - Type >>" +
                                         variableElement.asType() +
                                         "<< not supported for >>" +
                                         getClass().getSimpleName() +
                                         "<<");
    }
  }

  public abstract Class<T> annotationType();

  public List<Element> getVarsWithAnnotation(TypeElement element) {
    return this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnvironment,
                                                                        element,
                                                                        this.annotationType());
  }

  public abstract Target getTargetForCollectionAndList();

  public abstract String getImplementationName();

  public abstract ConstraintType getConstraintType();

  public abstract TypeName getValidationClass(VariableElement variableElement);

  protected abstract List<TypeKind> getSupportedPrimitives();

  protected abstract List<Class<?>> getSupportedDeclaredType();

  protected abstract AbstractGenerator createGenerator();

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
  public CodeBlock generateCheckList(Element clazz,
                                     VariableElement field)
      throws ProcessorException {
    return this.createGenerator()
               .generateCheckList(clazz,
                                  field);
  }

  @Override
  public CodeBlock generateValidList(Element clazz,
                                     VariableElement field)
      throws ProcessorException {
    return this.createGenerator()
               .generateValidList(clazz,
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

  /**
   * Describes the target of a constraint depending on the field type:
   * <ul>
   *   <li>field type NATIVE: the value will be ignored</li>
   *   <li>field type ARRAY or COLLECTION: the value will define the target of the constraint:
   *   <ol>
   *     <li>NOT_PERMITTED: constraint not allowed here</li>
   *     <li>ROOT: used on the variable itself</li>
   *     <li>ITEM: used on the ARRAY or Collection member</li>
   *   </ol></li>
   * </ul>
   */
  public enum Target {
    ROOT,
    ITEM,
    NOT_PERMITTED
  }
}
