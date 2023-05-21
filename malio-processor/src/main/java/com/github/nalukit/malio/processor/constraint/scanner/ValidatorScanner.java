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
package com.github.nalukit.malio.processor.constraint.scanner;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.model.ValidatorModel;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.MalioIgnore;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ValidatorScanner
    extends AbstractScanner {

  private final Element              validatorElement;
  private       List<ValidatorModel> subValidatorList;
  ;

  private ValidatorScanner(Builder builder) {
    this.validatorElement = builder.validatorElement;
    this.elements         = builder.elements;
    this.types            = builder.types;
    this.processorUtils   = builder.processorUtils;
  }

  public static Builder builder() {
    return new Builder();
  }

  public List<ValidatorModel> createSubValidatorList() {
    this.subValidatorList = new ArrayList<>();
    // get all variables of validator element
    List<VariableElement> list = this.getAllVariableElements();
    // examine every variable and look for relevant types ..
    // first step: sub types of Collection.
    for (VariableElement variableElement : list) {
      checkVariable(variableElement);
    }
    return this.subValidatorList;
  }

  public List<ValidatorModel> createSuperValidatorList() {
    List<ValidatorModel> superValidatorList = new ArrayList<>();
    // get list of super mirrors ...
    Set<TypeMirror> mirrors = this.processorUtils.getFlattenedSupertypeHierarchy(super.types,
                                                                                 validatorElement.asType());
    for (TypeMirror mirror : mirrors) {
      if (!types.isSameType(validatorElement.asType(),
                            mirror)) {
        TypeElement typeElementToCheck = (TypeElement) super.types.asElement(mirror);
        if (Objects.nonNull(typeElementToCheck.getAnnotation(MalioValidator.class))) {
          superValidatorList.add(new ValidatorModel(this.processorUtils.getPackageAsString(typeElementToCheck),
                                                    typeElementToCheck.getSimpleName()
                                                                      .toString(),
                                                    null,
                                                    Constants.MALIO_VALIDATOR_IMPL_NAME,
                                                    ValidatorModel.Type.NATIVE));
        }
      }
    }
    return superValidatorList;
  }

  private void checkVariable(VariableElement variableElement) {
    // check, if variable is excluded ...
    if (variableElement.getAnnotation(MalioIgnore.class) == null) {
      if (!variableElement.asType()
                          .getKind()
                          .isPrimitive()) {
        checkNeedForSubvalidator(variableElement);
      }
    }
  }

  private void checkNeedForSubvalidator(VariableElement variableElement) {
    TypeElement elementOfVariableType = null;
    if (variableElement.asType()
                       .getKind()
                       .equals(TypeKind.ARRAY)) {
      ArrayType  arrayType   = (ArrayType) variableElement.asType();
      TypeMirror elementType = arrayType.getComponentType();

      if (elementType.getKind()
                     .isPrimitive()) {
        return; // We do have to check for subvalidators if type is primitive :)
      }
      elementOfVariableType = (TypeElement) types.asElement(elementType);
    } else {
      elementOfVariableType = (TypeElement) types.asElement(variableElement.asType());
    }

    this.checkForDirectSubValidators(variableElement,
                                     elementOfVariableType);
    this.checkForSubValidatorAsGenricInArrays(variableElement,
                                              elementOfVariableType);
    this.checkForSubValidatorAsGenricInCollections(variableElement,
                                                   elementOfVariableType);
  }

  private void checkForDirectSubValidators(VariableElement variableElement,
                                           TypeElement elementOfVariableType) {
    String elementOfVariableTypeString = variableElement.asType()
                                                        .toString();
    if (!elementOfVariableTypeString.contains("[]")) {
      if (elementOfVariableType.getAnnotation(MalioValidator.class) != null) {
        this.addValidatorToValidatorGenerationList(variableElement,
                                                   elementOfVariableType,
                                                   ValidatorModel.Type.NATIVE,
                                                   null,
                                                   null);
      }
    }
  }

  private void checkForSubValidatorAsGenricInCollections(VariableElement variableElement,
                                                         TypeElement elementOfVariableType) {
    String elementOfVariableTypeString = variableElement.asType()
                                                        .toString();
    // check for generic ... if there is none, we can't do anything ...
    if (elementOfVariableTypeString.contains("<")) {
      // get flatten super type list and look for Collection class
      Set<TypeMirror> superClasses = this.processorUtils.getFlattenedSupertypeHierarchy(this.types,
                                                                                        elementOfVariableType.asType());
      boolean collectionClassFound = false;
      for (TypeMirror tm : superClasses) {
        if (tm.toString()
              .startsWith(Collection.class.getCanonicalName())) {
          collectionClassFound = true;
          break;
        }
      }
      if (collectionClassFound) {
        int open  = elementOfVariableTypeString.indexOf("<");
        int close = elementOfVariableTypeString.indexOf(">");
        String genericClassName = elementOfVariableTypeString.substring(open + 1,
                                                                        close);
        TypeElement elementOfGenericList = elements.getTypeElement(genericClassName);
        if (elementOfGenericList.getAnnotation(MalioValidator.class) != null) {
          this.addValidatorToValidatorGenerationList(variableElement,
                                                     elementOfVariableType,
                                                     ValidatorModel.Type.COLLECTION,
                                                     elementOfGenericList,
                                                     null);
        }
      }
    }
  }

  private void checkForSubValidatorAsGenricInArrays(VariableElement variableElement,
                                                    TypeElement elementOfVariableType) {
    String elementOfVariableTypeString = variableElement.asType()
                                                        .toString();
    // check for generic ... if there is none, we can't do anything ...
    if (variableElement.asType()
                       .getKind()
                       .equals(TypeKind.ARRAY)) {
      if (elementOfVariableType.getAnnotation(MalioValidator.class) != null) {
        this.addValidatorToValidatorGenerationList(variableElement,
                                                   elementOfVariableType,
                                                   ValidatorModel.Type.ARRAY,
                                                   elementOfVariableType,
                                                   null);
      }
    }
  }

  private void addValidatorToValidatorGenerationList(VariableElement variableElement,
                                                     TypeElement elementOfVariableType,
                                                     ValidatorModel.Type type,
                                                     TypeElement typeElement01,
                                                     TypeElement typeElement02) {
    boolean found = this.subValidatorList.stream()
                                         .anyMatch(model -> model.getPackageName()
                                                                 .equals(this.processorUtils.getPackageAsString(elementOfVariableType)) &&
                                                            model.getSimpleClassName()
                                                                 .equals(elementOfVariableType.getSimpleName()
                                                                                              .toString()) &&
                                                            model.getPostFix()
                                                                 .equals(Constants.MALIO_VALIDATOR_IMPL_NAME) &&
                                                            model.getFieldName()
                                                                 .equals(variableElement.toString()));
    if (!found) {
      this.subValidatorList.add(new ValidatorModel(this.processorUtils.getPackageAsString(elementOfVariableType),
                                                   elementOfVariableType.getSimpleName()
                                                                        .toString(),
                                                   variableElement.toString(),
                                                   Constants.MALIO_VALIDATOR_IMPL_NAME,
                                                   type,
                                                   typeElement01,
                                                   typeElement02));
    }
  }

  private List<VariableElement> getAllVariableElements() {
    List<VariableElement> list = new ArrayList<>();
    for (Element childElement : elements.getAllMembers((TypeElement) this.validatorElement)) {
      if (childElement.getKind() == ElementKind.FIELD) {
        list.add((VariableElement) childElement);
      }
    }
    return list;
  }

  public static class Builder {

    Element        validatorElement;
    Elements       elements;
    Types          types;
    ProcessorUtils processorUtils;

    public Builder validatorElement(Element validatorElement) {
      this.validatorElement = validatorElement;
      return this;
    }

    public Builder elements(Elements elements) {
      this.elements = elements;
      return this;
    }

    public Builder types(Types types) {
      this.types = types;
      return this;
    }

    public Builder processorUtils(ProcessorUtils processorUtils) {
      this.processorUtils = processorUtils;
      return this;
    }

    public ValidatorScanner build() {
      return new ValidatorScanner(this);
    }
  }
}
