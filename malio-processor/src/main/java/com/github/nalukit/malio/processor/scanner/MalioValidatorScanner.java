package com.github.nalukit.malio.processor.scanner;

import com.github.nalukit.malio.processor.MalioProcessor;
import com.github.nalukit.malio.processor.generator.MalioValidatorGenerator;
import com.github.nalukit.malio.processor.model.ValidatorModel;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.MalioIgnore;
import com.github.nalukit.malio.shared.annotation.MalioValidator;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MalioValidatorScanner {

  private Element              validatorElement;
  private List<ValidatorModel> subValidatorList;
  private Elements             elements;
  private Types                types;
  private ProcessorUtils       processorUtils;

  private MalioValidatorScanner(Builder builder) {
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
      // check, if variable is excluded ...
      if (variableElement.getAnnotation(MalioIgnore.class) == null) {
        TypeElement elementOfVariableType = (TypeElement) types.asElement(variableElement.asType());
        this.checkForDirectSubValidators(variableElement,
                                         elementOfVariableType);
        this.checkForSubValidatorAsGenricInCollections(variableElement,
                                                       elementOfVariableType);

      }
    }
    return this.subValidatorList;
  }

  private void checkForDirectSubValidators(VariableElement variableElement,
                                           TypeElement elementOfVariableType) {
    if (elementOfVariableType.getAnnotation(MalioValidator.class) != null) {
      this.addValidatorToValidatorGenerationList(variableElement,
                                                 elementOfVariableType,
                                                 ValidatorModel.Type.NATIVE,
                                                 null,
                                                 null);
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
                                                     ValidatorModel.Type.LIST,
                                                     elementOfGenericList,
                                                     null);
          System.out.println("Yeah, It's a list");
        }

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
                                                                 .equals(MalioValidatorGenerator.MALIO_VALIDATOR_IMPL_NAME) &&
                                                            model.getFieldName()
                                                                 .equals(variableElement.toString()));
    if (!found) {
      this.subValidatorList.add(new ValidatorModel(this.processorUtils.getPackageAsString(elementOfVariableType),
                                                   elementOfVariableType.getSimpleName()
                                                                        .toString(),
                                                   variableElement.toString(),
                                                   MalioValidatorGenerator.MALIO_VALIDATOR_IMPL_NAME,
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

    public MalioValidatorScanner build() {
      return new MalioValidatorScanner(this);
    }
  }
}
