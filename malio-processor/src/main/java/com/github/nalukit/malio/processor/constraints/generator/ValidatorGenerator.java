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

import com.github.nalukit.malio.processor.MalioValidatorGenerator;
import com.github.nalukit.malio.processor.model.ValidatorModel;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ValidatorGenerator
    extends AbstractGenerator {

  private final List<ValidatorModel>  subValidatorList;
  private       List<ValidatorModel>  superValidatorList;

  private MalioValidatorGenerator malioValidatorGenerator;

  private ValidatorGenerator(Builder builder) {
    this.superValidatorList = builder.superValidatorList;
    this.subValidatorList   = builder.subValidatorList;
    this.elements           = builder.elements;
    this.types              = builder.types;
    this.filer              = builder.filer;
    this.processorUtils     = builder.processorUtils;
    this.malioValidatorGenerator = builder.malioValidatorGenerator;
  }

  public static Builder builder() {
    return new Builder();
  }


  @Override
  protected CodeBlock generate(Element clazz, VariableElement field, String suffix) {
    return null;
  }

  public void appendSuperAndSubValidatorsCheck() {
    List<CodeBlock> checkMethod = createCheckMethod();
    for( CodeBlock codeBlock : checkMethod){
      this.malioValidatorGenerator.appendCheckBlock(codeBlock);
    }
  }

  private List<CodeBlock> createCheckMethod() {
    List<CodeBlock> checks = new ArrayList<>();
    for (ValidatorModel model : this.superValidatorList) {
      CodeBlock validateMethodForSuperValidator = this.createCheckMethodForSuperValidator(model);
      checks.add(validateMethodForSuperValidator);
    }
    for (ValidatorModel model : this.subValidatorList) {
      CodeBlock validateMethodForSubValidator = this.createCheckMethodForSubValidator(model);
      checks.add(validateMethodForSubValidator);
    }

    return checks;
  }

  private CodeBlock createCheckMethodForSuperValidator(ValidatorModel model) {
    String vaidatorClassName = model.getSimpleClassName() + model.getPostFix();
    return CodeBlock.builder().add("$T.INSTANCE.check(bean);",
                                    ClassName.get(model.getPackageName(),
                                                  vaidatorClassName)).build();
  }

  private CodeBlock createCheckMethodForSubValidator(ValidatorModel model) {
    CodeBlock.Builder builder = CodeBlock.builder();
    builder.beginControlFlow("if ($T.nonNull(bean.$L()))",
                                        ClassName.get(Objects.class),
                                        this.processorUtils.createGetMethodName(model.getFieldName()));
    switch (model.getType()) {
      case NATIVE:
        String vaidatorClassName = model.getSimpleClassName() + model.getPostFix();
        builder.add("$T.INSTANCE.check(bean.$L());",
                                        ClassName.get(model.getPackageName(),
                                                      vaidatorClassName),
                                        this.processorUtils.createGetMethodName(model.getFieldName()));
        break;
      case LIST:
        String vaidatorClassNameList = model.getGenericTypeElement01()
                                            .toString() + model.getPostFix();
        builder.beginControlFlow("for ($T model : bean.$L())",
                                            ClassName.get(model.getGenericTypeElement01()),
                                            this.processorUtils.createGetMethodName(model.getFieldName()))
                          .add("$L.INSTANCE.check(model);",
                                        vaidatorClassNameList)
                          .endControlFlow();
        break;
    }
    builder.endControlFlow();

    return builder.build();
  }



  public void appendSuperAndSubValidatorsValid() {
    List<CodeBlock> checkMethod = createValidMethod();
    for( CodeBlock codeBlock : checkMethod){
      this.malioValidatorGenerator.appendValidBlock(codeBlock);
    }
  }

  private List<CodeBlock> createValidMethod() {
    List<CodeBlock> checks = new ArrayList<>();
    for (ValidatorModel model : this.superValidatorList) {
      CodeBlock validateMethodForSuperValidator = this.createSuperValidateMethod(model);
      checks.add(validateMethodForSuperValidator);
    }
    for (ValidatorModel model : this.subValidatorList) {
      CodeBlock validateMethodForSubValidator = this.createSubValidateMethod(model);
      checks.add(validateMethodForSubValidator);
    }

    return checks;
  }


  private CodeBlock createSuperValidateMethod(ValidatorModel model) {
    String vaidatorClassName = model.getSimpleClassName() + model.getPostFix();
    return CodeBlock.builder().add("$T.INSTANCE.validate(bean, validationResult);",
                                                ClassName.get(model.getPackageName(),
                                                              vaidatorClassName)).build();
  }

  private CodeBlock createSubValidateMethod(ValidatorModel model) {
    CodeBlock.Builder builder = CodeBlock.builder();
    builder.beginControlFlow("if ($T.nonNull(bean.$L()))",
                                                    ClassName.get(Objects.class),
                                                    this.processorUtils.createGetMethodName(model.getFieldName()));
    switch (model.getType()) {
      case NATIVE:
        String vaidatorClassName = model.getSimpleClassName() + model.getPostFix();
        builder.add("validationResult = $T.INSTANCE.validate(bean.$L(), validationResult);",
                                                    ClassName.get(model.getPackageName(),
                                                                  vaidatorClassName),
                                                    this.processorUtils.createGetMethodName(model.getFieldName()));
        break;
      case LIST:
        String vaidatorClassNameList = model.getGenericTypeElement01()
                                            .toString() + model.getPostFix();
        builder.beginControlFlow("for ($T model : bean.$L())",
                                                        ClassName.get(model.getGenericTypeElement01()),
                                                        this.processorUtils.createGetMethodName(model.getFieldName()))
                                      .add("validationResult = $L.INSTANCE.validate(model, validationResult);",
                                                    vaidatorClassNameList)
                                      .endControlFlow();
        break;
    }

    builder.endControlFlow();

    return builder.build();
  }

  public static class Builder {
    List<ValidatorModel>  superValidatorList;
    List<ValidatorModel>  subValidatorList;
    Elements              elements;
    Types                 types;
    Filer                 filer;
    ProcessorUtils        processorUtils;
    MalioValidatorGenerator malioValidatorGenerator;

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

    public Builder malioValidatorGenerator(MalioValidatorGenerator malioValidatorGenerator) {
      this.malioValidatorGenerator = malioValidatorGenerator;
      return this;
    }
  }
}
