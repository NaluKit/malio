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
import com.github.nalukit.malio.processor.model.ConstraintModel;
import com.github.nalukit.malio.processor.model.ValidatorModel;
import com.github.nalukit.malio.processor.util.BuildWithMalioCommentProvider;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.internal.validator.AbstractValidator;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Objects;

public class ValidatorGenerator
    extends AbstractGenerator {

  private final List<ConstraintModel> constraintList;
  private       List<ValidatorModel>  superValidatorList;
  private final List<ValidatorModel>  subValidatorList;

  private ValidatorGenerator(Builder builder) {
    this.constraintList     = builder.constraintList;
    this.superValidatorList = builder.superValidatorList;
    this.subValidatorList   = builder.subValidatorList;
    this.elements           = builder.elements;
    this.types              = builder.types;
    this.filer              = builder.filer;
    this.processorUtils     = builder.processorUtils;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate(Element validatorElement, VariableElement variableElement)
      throws ProcessorException {
    TypeSpec.Builder typeSpec = this.createValidatorTypeSpec(validatorElement);

    addConstructor(typeSpec);
    addSingletonVariable(validatorElement, typeSpec);

    MethodSpec.Builder checkMethodBuilder = MethodSpec.methodBuilder("check")
                                                      .addModifiers(Modifier.PUBLIC)
                                                      .addParameter(ParameterSpec.builder(ClassName.get(validatorElement.asType()),
                                                                                          "bean")
                                                                                 .build())
                                                      .returns(void.class)
                                                      .addException(ClassName.get(MalioValidationException.class));
    this.createCheckMethod(checkMethodBuilder);
    typeSpec.addMethod(checkMethodBuilder.build());

    MethodSpec.Builder validOneParameterMethodBuilder = MethodSpec.methodBuilder("validate")
                                                                  .addModifiers(Modifier.PUBLIC)
                                                                  .addParameter(ParameterSpec.builder(ClassName.get(validatorElement.asType()),
                                                                                                      "bean")
                                                                                             .build())
                                                                  .returns(ClassName.get(ValidationResult.class))
                                                                  .addStatement("$T validationResult = new $T()",
                                                                                ClassName.get(ValidationResult.class),
                                                                                ClassName.get(ValidationResult.class))
                                                                  .addStatement("return this.validate(bean, validationResult)");
    typeSpec.addMethod(validOneParameterMethodBuilder.build());

    MethodSpec.Builder validMethodTwoParameterBuilder = MethodSpec.methodBuilder("validate")
                                                                  .addModifiers(Modifier.PUBLIC)
                                                                  .addParameter(ParameterSpec.builder(ClassName.get(validatorElement.asType()),
                                                                                                      "bean")
                                                                                             .build())
                                                                  .addParameter(ParameterSpec.builder(ClassName.get(ValidationResult.class),
                                                                                                      "validationResult")
                                                                                             .build())
                                                                  .returns(ClassName.get(ValidationResult.class));
    this.createIsValidMethod(validMethodTwoParameterBuilder);
    typeSpec.addMethod(validMethodTwoParameterBuilder.build());

    super.writeFile(validatorElement,
                    Constants.MALIO_VALIDATOR_IMPL_NAME,
                    typeSpec);
  }

  private static void addConstructor(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addModifiers(Modifier.PUBLIC)
                                 .addStatement("super()")
                                 .build());
  }

  private void addSingletonVariable(Element validatorElement, TypeSpec.Builder typeSpec) {
    typeSpec.addField(FieldSpec.builder(ClassName.get(this.processorUtils.getPackageAsString(validatorElement),
                                                      this.createValidatorClassName(validatorElement.getSimpleName()
                                                                                                    .toString())),
                                        "INSTANCE",
                                        Modifier.PUBLIC,
                                        Modifier.STATIC,
                                        Modifier.FINAL)
                               .initializer("new $T()",
                                            ClassName.get(this.processorUtils.getPackageAsString(validatorElement),
                                                          this.createValidatorClassName(validatorElement.getSimpleName()
                                                                                                        .toString())))
                               .build());
  }

  private void createCheckMethod(MethodSpec.Builder checkMethodBuilder) {
    int checkValidatorCounter = 1;
    for (ConstraintModel model : this.constraintList) {
      checkValidatorCounter = createCheckMethodForSubValidator(checkMethodBuilder,
                                                               checkValidatorCounter,
                                                               model);
    }
    for (ValidatorModel model : this.superValidatorList) {
      this.createValidateMethodForSuperValidator(checkMethodBuilder,
                                                 model);
    }
    for (ValidatorModel model : this.subValidatorList) {
      this.createValidateMethodForSubValidator(checkMethodBuilder,
                                               model);
    }
  }

  private void createValidateMethodForSuperValidator(MethodSpec.Builder checkMethodBuilder,
                                                     ValidatorModel model) {
    String vaidatorClassName = model.getSimpleClassName() + model.getPostFix();
    checkMethodBuilder.addStatement("$T.INSTANCE.check(bean)",
                                    ClassName.get(model.getPackageName(),
                                                  vaidatorClassName));
  }

  private void createValidateMethodForSubValidator(MethodSpec.Builder checkMethodBuilder,
                                                   ValidatorModel model) {
    checkMethodBuilder.beginControlFlow("if ($T.nonNull(bean.$L()))",
                                        ClassName.get(Objects.class),
                                        this.processorUtils.createGetMethodName(model.getFieldName()));
    switch (model.getType()) {
      case NATIVE:
        String vaidatorClassName = model.getSimpleClassName() + model.getPostFix();
        checkMethodBuilder.addStatement("$T.INSTANCE.check(bean.$L())",
                                        ClassName.get(model.getPackageName(),
                                                      vaidatorClassName),
                                        this.processorUtils.createGetMethodName(model.getFieldName()));
        break;
      case LIST:
        String vaidatorClassNameList = model.getGenericTypeElement01()
                                            .toString() + model.getPostFix();
        checkMethodBuilder.beginControlFlow("for ($T model : bean.$L())",
                                            ClassName.get(model.getGenericTypeElement01()),
                                            this.processorUtils.createGetMethodName(model.getFieldName()))
                          .addStatement("$L.INSTANCE.check(model)",
                                        vaidatorClassNameList)
                          .endControlFlow();
        break;
    }
    checkMethodBuilder.endControlFlow();
  }

  private int createCheckMethodForSubValidator(MethodSpec.Builder checkMethodBuilder,
                                               int checkValidatorCounter,
                                               ConstraintModel model) {
    String variableName = "constraint" + this.getStringFromInt(checkValidatorCounter);
    String constraintClassName = super.createConstraintClassName(model.getSimpleClassName(),
                                                                 model.getFieldName(),
                                                                 model.getPostFix());
    checkMethodBuilder.addStatement("$T $L = new $T()",
                                    ClassName.get(model.getPackageName(),
                                                  constraintClassName),
                                    variableName,
                                    ClassName.get(model.getPackageName(),
                                                  constraintClassName));
    checkMethodBuilder.addStatement("$L.check(bean.$L())",
                                    variableName,
                                    this.processorUtils.createGetMethodName(model.getFieldName()));
    checkValidatorCounter++;
    return checkValidatorCounter;
  }

  private void createIsValidMethod(MethodSpec.Builder validMethodTwoParameterBuilder) {
    int validateValidatorCounter = 1;
    for (ConstraintModel model : this.constraintList) {
      validateValidatorCounter = this.createCheckMethod(validMethodTwoParameterBuilder,
                                                        validateValidatorCounter,
                                                        model);
    }
    for (ValidatorModel model : this.superValidatorList) {
      this.createSuperValidateMethod(validMethodTwoParameterBuilder,
                                     model);
    }
    for (ValidatorModel model : this.subValidatorList) {
      this.createSubValidateMethod(validMethodTwoParameterBuilder,
                                   model);
    }
    validMethodTwoParameterBuilder.addStatement("return validationResult");
  }

  private void createSuperValidateMethod(MethodSpec.Builder validMethodTwoParameterBuilder,
                                         ValidatorModel model) {
    String vaidatorClassName = model.getSimpleClassName() + model.getPostFix();
    validMethodTwoParameterBuilder.addStatement("$T.INSTANCE.validate(bean, validationResult)",
                                                ClassName.get(model.getPackageName(),
                                                              vaidatorClassName));
  }

  private void createSubValidateMethod(MethodSpec.Builder validMethodTwoParameterBuilder,
                                       ValidatorModel model) {
    validMethodTwoParameterBuilder.beginControlFlow("if ($T.nonNull(bean.$L()))",
                                                    ClassName.get(Objects.class),
                                                    this.processorUtils.createGetMethodName(model.getFieldName()));
    switch (model.getType()) {
      case NATIVE:
        String vaidatorClassName = model.getSimpleClassName() + model.getPostFix();
        validMethodTwoParameterBuilder.addStatement("validationResult = $T.INSTANCE.validate(bean.$L(), validationResult)",
                                                    ClassName.get(model.getPackageName(),
                                                                  vaidatorClassName),
                                                    this.processorUtils.createGetMethodName(model.getFieldName()));
        break;
      case LIST:
        String vaidatorClassNameList = model.getGenericTypeElement01()
                                            .toString() + model.getPostFix();
        validMethodTwoParameterBuilder.beginControlFlow("for ($T model : bean.$L())",
                                                        ClassName.get(model.getGenericTypeElement01()),
                                                        this.processorUtils.createGetMethodName(model.getFieldName()))
                                      .addStatement("validationResult = $L.INSTANCE.validate(model, validationResult)",
                                                    vaidatorClassNameList)
                                      .endControlFlow();
        break;
    }

    validMethodTwoParameterBuilder.endControlFlow();
  }

  private int createCheckMethod(MethodSpec.Builder validMethodTwoParameterBuilder,
                                int validateValidatorCounter,
                                ConstraintModel model) {
    String variableName = "constraint" + this.getStringFromInt(validateValidatorCounter);
    String constraintClassName = this.createConstraintClassName(model.getSimpleClassName(),
                                                                model.getFieldName(),
                                                                model.getPostFix());
    validMethodTwoParameterBuilder.addStatement("$T $L = new $T()",
                                                ClassName.get(model.getPackageName(),
                                                              constraintClassName),
                                                variableName,
                                                ClassName.get(model.getPackageName(),
                                                              constraintClassName));
    validMethodTwoParameterBuilder.addStatement("$L.isValid(bean.$L(), validationResult)",
                                                variableName,
                                                this.processorUtils.createGetMethodName(model.getFieldName()));
    validateValidatorCounter++;
    return validateValidatorCounter;
  }

  private TypeSpec.Builder createValidatorTypeSpec(Element validatorElement) {
    return TypeSpec.classBuilder(this.createValidatorClassName(validatorElement.getSimpleName()
                                                                               .toString()))
                   .addJavadoc(BuildWithMalioCommentProvider.INSTANCE.getGeneratedComment())
                   .superclass(ClassName.get(AbstractValidator.class))
                   .addModifiers(Modifier.PUBLIC,
                                 Modifier.FINAL);
  }

  private String createValidatorClassName(String modelName) {
    return this.processorUtils.setFirstCharacterToUpperCase(modelName) + Constants.MALIO_VALIDATOR_IMPL_NAME;
  }

  private String getStringFromInt(int value) {
    if (value < 1) {
      return "00000";
    } else if (value < 10) {
      return "0000" + value;
    } else if (value < 100) {
      return "000" + value;
    } else if (value < 1000) {
      return "00" + value;
    } else if (value < 10000) {
      return "0" + value;
    } else {
      return String.valueOf(value);
    }
  }

  public static class Builder {

    List<ConstraintModel> constraintList;
    List<ValidatorModel>  superValidatorList;
    List<ValidatorModel>  subValidatorList;
    Elements              elements;
    Types                 types;
    Filer                 filer;
    ProcessorUtils        processorUtils;

    public Builder constraintList(List<ConstraintModel> constraintList) {
      this.constraintList = constraintList;
      return this;
    }

    public Builder superValidatorList(List<ValidatorModel> superValidatorList) {
      this.superValidatorList = superValidatorList;
      return this;
    }

    public Builder subValidatorList(List<ValidatorModel> subValidatorList) {
      this.subValidatorList = subValidatorList;
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

    public Builder filer(Filer filer) {
      this.filer = filer;
      return this;
    }

    public Builder processorUtils(ProcessorUtils processorUtils) {
      this.processorUtils = processorUtils;
      return this;
    }

    public ValidatorGenerator build() {
      return new ValidatorGenerator(this);
    }
  }
}
