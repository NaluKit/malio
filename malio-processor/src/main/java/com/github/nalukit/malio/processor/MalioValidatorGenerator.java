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
package com.github.nalukit.malio.processor;

import com.github.nalukit.malio.processor.util.BuildWithMalioCommentProvider;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.internal.validator.AbstractValidator;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import java.io.IOException;

public class MalioValidatorGenerator {

    private final ProcessorUtils processorUtils;
    private final TypeSpec.Builder typeSpec;
    private final MethodSpec.Builder checkMethodBuilder;

    private final MethodSpec.Builder validMethodTwoParameterBuilder;
    private final Filer filer;
    private final Element clazz;

    public MalioValidatorGenerator(ProcessingEnvironment processingEnv, ProcessorUtils processorUtils, Element clazz) {
        this.filer = processingEnv.getFiler();
        this.processorUtils = processorUtils;
        this.typeSpec = this.createValidatorTypeSpec(clazz);
        this.clazz = clazz;
        addConstructor(typeSpec);
        addSingletonVariable(clazz, typeSpec);


        this.checkMethodBuilder = MethodSpec.methodBuilder("check")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ClassName.get(clazz.asType()),
                                "bean")
                        .build())
                .returns(void.class)
                .addException(ClassName.get(MalioValidationException.class));

        this.validMethodTwoParameterBuilder = MethodSpec.methodBuilder("validate")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ClassName.get(clazz.asType()),
                                "bean")
                        .build())
                .addParameter(ParameterSpec.builder(ClassName.get(ValidationResult.class),
                                "validationResult")
                        .build())
                .returns(ClassName.get(ValidationResult.class));

        MethodSpec.Builder validOneParameterMethodBuilder = MethodSpec.methodBuilder("validate")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ClassName.get(clazz.asType()),
                                "bean")
                        .build())
                .returns(ClassName.get(ValidationResult.class))
                .addStatement("$T validationResult = new $T()",
                        ClassName.get(ValidationResult.class),
                        ClassName.get(ValidationResult.class))
                .addStatement("return this.validate(bean, validationResult)");
        typeSpec.addMethod(validOneParameterMethodBuilder.build());

    }

    private void addConstructor(TypeSpec.Builder typeSpec) {
        typeSpec.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super()")
                .build());
    }

    private void addSingletonVariable(Element validatorElement,
                                      TypeSpec.Builder typeSpec) {
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

    public void appendCheckBlock(CodeBlock checkBlock) {
        this.checkMethodBuilder.addCode(checkBlock);
    }

    public void appendCheckStatement(CodeBlock checkBlock) {
        this.checkMethodBuilder.addStatement(checkBlock);
    }

    public void appendValidBlock(CodeBlock checkBlock) {
        this.validMethodTwoParameterBuilder.addCode(checkBlock);
    }

    public void appendValidStatement(CodeBlock checkBlock) {
        this.validMethodTwoParameterBuilder.addStatement(checkBlock);
    }

    public void appendBeginControlFlowArray(VariableElement field) {
        this.checkMethodBuilder.beginControlFlow("for (int i = 0; i < bean.$L().length; i++)",
                                                 this.processorUtils.createGetMethodName(field.getSimpleName()
                                                                                              .toString()));
        this.validMethodTwoParameterBuilder.beginControlFlow("for (int i = 0; i < bean.$L().length; i++)",
                                                             this.processorUtils.createGetMethodName(field.getSimpleName()
                                                                                                          .toString()));
    }

    public void appendBeginControlFlowCollection(VariableElement field) {
        this.checkMethodBuilder.beginControlFlow("for (int i = 0; i < bean.$L().size(); i++)",
                                                 this.processorUtils.createGetMethodName(field.getSimpleName()
                                                                                              .toString()));
        this.validMethodTwoParameterBuilder.beginControlFlow("for (int i = 0; i < bean.$L().size(); i++)",
                                                             this.processorUtils.createGetMethodName(field.getSimpleName()
                                                                                                          .toString()));
    }

    public void appendEndControlFlow() {
        this.checkMethodBuilder.endControlFlow();
        this.validMethodTwoParameterBuilder.endControlFlow();
    }

    private TypeSpec build() {
        this.validMethodTwoParameterBuilder.addStatement("return validationResult");
        return this.typeSpec.addMethod(this.checkMethodBuilder.build())
                            .addMethod(this.validMethodTwoParameterBuilder.build())
                            .build();
    }

    public void writeFile()
        throws ProcessorException {
        TypeSpec typeSpec = this.build();
        this.writeFile(this.clazz,
                Constants.MALIO_VALIDATOR_IMPL_NAME,
                typeSpec);
    }

    protected void writeFile(Element element,
                             String postFix,
                             TypeSpec typeSpec)
            throws ProcessorException {
        JavaFile javaFile = JavaFile.builder(this.processorUtils.getPackageAsString(element),
                        typeSpec)
                .build();
        try {
            javaFile.writeTo(this.filer);
        } catch (IOException e) {
            throw new ProcessorException("Unable to write generated file: >>" +
                    element.toString() +
                    postFix +
                    "<< -> exception: " +
                    e.getMessage());
        }
    }
}
