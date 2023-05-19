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

import com.github.nalukit.malio.processor.constraints.AbstractConstraint;
import com.github.nalukit.malio.processor.constraints.ArraySizeConstraint;
import com.github.nalukit.malio.processor.constraints.BlacklistConstraint;
import com.github.nalukit.malio.processor.constraints.EmailConstraint;
import com.github.nalukit.malio.processor.constraints.MaxConstraint;
import com.github.nalukit.malio.processor.constraints.MaxDecimalConstraint;
import com.github.nalukit.malio.processor.constraints.MaxLengthConstraint;
import com.github.nalukit.malio.processor.constraints.MinConstraint;
import com.github.nalukit.malio.processor.constraints.MinDecimalConstraint;
import com.github.nalukit.malio.processor.constraints.MinLengthConstraint;
import com.github.nalukit.malio.processor.constraints.NotBlankConstraint;
import com.github.nalukit.malio.processor.constraints.NotEmptyConstraint;
import com.github.nalukit.malio.processor.constraints.NotNullConstraint;
import com.github.nalukit.malio.processor.constraints.NotZeroConstraint;
import com.github.nalukit.malio.processor.constraints.RegexpConstraint;
import com.github.nalukit.malio.processor.constraints.SizeConstraint;
import com.github.nalukit.malio.processor.constraints.UuidConstraint;
import com.github.nalukit.malio.processor.constraints.WhitelistConstraint;
import com.github.nalukit.malio.processor.constraints.generator.ValidatorGenerator;
import com.github.nalukit.malio.processor.constraints.scanner.ValidatorScanner;
import com.github.nalukit.malio.processor.model.ValidatorModel;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.Malio;
import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.google.auto.service.AutoService;
import com.google.common.base.Stopwatch;
import com.squareup.javapoet.CodeBlock;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@AutoService(Processor.class)
public class MalioProcessor
    extends AbstractProcessor {

  @SuppressWarnings("rawtypes") List<AbstractConstraint> constraints;
  private ProcessorUtils processorUtils;
  private Stopwatch      stopwatch;

  public MalioProcessor() {
    super();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Stream.of(MalioValidator.class.getCanonicalName())
                 .collect(toSet());
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    this.stopwatch = Stopwatch.createStarted();
    this.setUp();
    this.processorUtils.createNoteMessage("Nalu-Plugin-GWT-Processor started ...");
    String implementationVersion = Malio.getVersion();
    this.processorUtils.createNoteMessage("Nalu-Plugin-GWT-Processor version >>" + implementationVersion + "<<");
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(processingEnv)
                                        .build();

    BlacklistConstraint blacklistConstraint = new BlacklistConstraint(this.processingEnv,
                                                                      this.processorUtils);
    EmailConstraint emailConstraint = new EmailConstraint(this.processingEnv,
                                                          this.processorUtils);
    MaxDecimalConstraint maxDecimalConstraint = new MaxDecimalConstraint(this.processingEnv,
                                                                         this.processorUtils);
    MaxLengthConstraint maxLengthConstraint = new MaxLengthConstraint(this.processingEnv,
                                                                      this.processorUtils);
    MaxConstraint maxConstraint = new MaxConstraint(this.processingEnv,
                                                    this.processorUtils);
    MinDecimalConstraint minDecimalConstraint = new MinDecimalConstraint(this.processingEnv,
                                                                         this.processorUtils);
    MinLengthConstraint minLengthConstraint = new MinLengthConstraint(this.processingEnv,
                                                                      this.processorUtils);
    MinConstraint minConstraint = new MinConstraint(this.processingEnv,
                                                    this.processorUtils);
    NotBlankConstraint notBlankConstraint = new NotBlankConstraint(this.processingEnv,
                                                                   this.processorUtils);
    NotEmptyConstraint notEmptyConstraint = new NotEmptyConstraint(this.processingEnv,
                                                                   this.processorUtils);
    NotNullConstraint notNullConstraint = new NotNullConstraint(this.processingEnv,
                                                                this.processorUtils);
    RegexpConstraint regexpConstraint = new RegexpConstraint(this.processingEnv,
                                                             this.processorUtils);
    SizeConstraint sizeConstraint = new SizeConstraint(this.processingEnv,
                                                       this.processorUtils);
    ArraySizeConstraint arraySizeConstraint = new ArraySizeConstraint(this.processingEnv,
                                                                      this.processorUtils);
    UuidConstraint uuidConstraint = new UuidConstraint(this.processingEnv,
                                                       this.processorUtils);
    WhitelistConstraint whitelistConstraint = new WhitelistConstraint(this.processingEnv,
                                                                      this.processorUtils);
    NotZeroConstraint notZeroConstraint = new NotZeroConstraint(this.processingEnv,
                                                                this.processorUtils);

    this.constraints = Arrays.asList(notNullConstraint,
                                     notBlankConstraint,
                                     regexpConstraint,
                                     emailConstraint,
                                     uuidConstraint,
                                     maxConstraint,
                                     minConstraint,
                                     maxLengthConstraint,
                                     minLengthConstraint,
                                     blacklistConstraint,
                                     whitelistConstraint,
                                     maxDecimalConstraint,
                                     minDecimalConstraint,
                                     notEmptyConstraint,
                                     sizeConstraint,
                                     arraySizeConstraint,
                                     notZeroConstraint);
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations,
                         RoundEnvironment roundEnv) {
    try {
      if (roundEnv.processingOver()) {
        this.processorUtils.createNoteMessage("Malio-Processor finished ... processing takes: " +
                                              this.stopwatch.stop()
                                                            .toString());
      } else {
        processSingleRun(annotations,
                         roundEnv);
      }
    } catch (ProcessorException e) {
      this.processorUtils.createErrorMessage(e.getMessage());
      this.processorUtils.createErrorMessage(e);
      return true;
    }
    return true;
  }

  private void processSingleRun(Set<? extends TypeElement> annotations,
                                RoundEnvironment roundEnv)
      throws ProcessorException {
    if (annotations.size() > 0) {
      for (TypeElement annotation : annotations) {
        if (MalioValidator.class.getCanonicalName()
                                .equals(annotation.toString())) {
          processMalioValidator(roundEnv);
        }
      }
    }
  }

  private void processMalioValidator(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element validatorElement : roundEnv.getElementsAnnotatedWith(MalioValidator.class)) {
      processClass(validatorElement);
    }
  }

  private void processClass(Element clazz)
      throws ProcessorException {
    MalioValidatorGenerator malioValidatorGenerator = new MalioValidatorGenerator(this.processingEnv,
                                                                                  processorUtils,
                                                                                  clazz);
    this.processVariable(clazz.asType(),
                         malioValidatorGenerator);
    this.createSubAndSuperValidators(clazz,
                                     malioValidatorGenerator);
    malioValidatorGenerator.writeFile();
  }

  private void processVariable(TypeMirror clazz,
                               MalioValidatorGenerator malioValidatorGenerator)
      throws ProcessorException {
    TypeElement element = (TypeElement) this.processingEnv.getTypeUtils()
                                                          .asElement(clazz);
    List<VariableElement> listOfDirectVariables = this.processingEnv.getElementUtils()
                                                                    .getAllMembers(element)
                                                                    .stream()
                                                                    .filter(e -> e.getKind() == ElementKind.FIELD)
                                                                    .filter(e -> e.getEnclosingElement()
                                                                                  .toString()
                                                                                  .equals(element.toString()))
                                                                    .map(e -> (VariableElement) e)
                                                                    .collect(Collectors.toList());
    for (VariableElement field : listOfDirectVariables) {
      ValidatorModel.Type type = this.getTypeFromVariableElement(field);
      switch (type) {
        case ARRAY:
          this.createConstraintsForArray(field,
                                         malioValidatorGenerator);
          break;
        case COLLECTION:
          System.out.println("LIST");
          break;
        case NATIVE:
          this.createConstraintsForNative(field,
                                          malioValidatorGenerator);
          break;
      }
    }
  }

  private void createConstraintsForArray(VariableElement field,
                                         MalioValidatorGenerator malioValidatorGenerator)
      throws ProcessorException {
    for (AbstractConstraint<?> constraint : this.constraints) {
      if (AbstractConstraint.Target.ROOT == constraint.getTargetForCollectionAndList()) {
        if (Objects.nonNull(field.getAnnotation(constraint.annotationType()))) {
          constraint.checkDataType(field,
                                   ValidatorModel.Type.ARRAY,
                                   constraint.getTargetForCollectionAndList());
          CodeBlock checkBlock = constraint.generateCheckNative(field,
                                                                field);
          CodeBlock validBlock = constraint.generateValidNative(field,
                                                                field);

          malioValidatorGenerator.appendCheckStatement(checkBlock);
          malioValidatorGenerator.appendValidStatement(validBlock);
        }
      }
    }

    boolean beginControlFlowCreated = false;
    for (AbstractConstraint<?> constraint : this.constraints) {
      if (AbstractConstraint.Target.ROOT != constraint.getTargetForCollectionAndList()) {
        if (Objects.nonNull(field.getAnnotation(constraint.annotationType()))) {
          if (!beginControlFlowCreated) {
            malioValidatorGenerator.appendBeginControlFlowArray(field);
            beginControlFlowCreated = true;
          }
          constraint.checkDataType(field,
                                   ValidatorModel.Type.ARRAY,
                                   constraint.getTargetForCollectionAndList());
          CodeBlock checkBlock = constraint.generateCheckArray(field,
                                                                field);
          CodeBlock validBlock = constraint.generateValidArray(field,
                                                                field);

          malioValidatorGenerator.appendCheckStatement(checkBlock);
          malioValidatorGenerator.appendValidStatement(validBlock);
        }
      }
    }
    if (beginControlFlowCreated) {
      malioValidatorGenerator.appendEndControlFlow();
    }
  }

  private void createConstraintsForNative(VariableElement field,
                                          MalioValidatorGenerator malioValidatorGenerator)
      throws ProcessorException {
    for (AbstractConstraint<?> constraint : this.constraints) {
      if (Objects.nonNull(field.getAnnotation(constraint.annotationType()))) {
        constraint.checkDataType(field,
                                 ValidatorModel.Type.NATIVE,
                                 constraint.getTargetForCollectionAndList());
        CodeBlock checkBlock = constraint.generateCheckNative(field,
                                                              field);
        CodeBlock validBlock = constraint.generateValidNative(field,
                                                              field);

        malioValidatorGenerator.appendCheckStatement(checkBlock);
        malioValidatorGenerator.appendValidStatement(validBlock);
      }
    }
  }

  private void createSubAndSuperValidators(Element validatorElement,
                                           MalioValidatorGenerator malioValidatorGenerator)
      throws ProcessorException {
    List<ValidatorModel> subValidatorList = ValidatorScanner.builder()
                                                            .elements(this.processingEnv.getElementUtils())
                                                            .types(this.processingEnv.getTypeUtils())
                                                            .validatorElement(validatorElement)
                                                            .processorUtils(this.processorUtils)
                                                            .build()
                                                            .createSubValidatorList();

    List<ValidatorModel> superValidatorList = ValidatorScanner.builder()
                                                              .elements(this.processingEnv.getElementUtils())
                                                              .types(this.processingEnv.getTypeUtils())
                                                              .validatorElement(validatorElement)
                                                              .processorUtils(this.processorUtils)
                                                              .build()
                                                              .createSuperValidatorList();

    ValidatorGenerator generator = ValidatorGenerator.builder()
                                                     .elements(this.processingEnv.getElementUtils())
                                                     .filer(this.processingEnv.getFiler())
                                                     .types(this.processingEnv.getTypeUtils())
                                                     .superValidatorList(superValidatorList)
                                                     .subValidatorList(subValidatorList)
                                                     .malioValidatorGenerator(malioValidatorGenerator)
                                                     .processorUtils(this.processorUtils)
                                                     .build();
    generator.appendSuperAndSubValidatorsCheck();
    generator.appendSuperAndSubValidatorsValid();
  }

  private ValidatorModel.Type getTypeFromVariableElement(VariableElement variableElement) {
    if (variableElement.asType()
                       .getKind()
                       .equals(TypeKind.ARRAY)) {
      return ValidatorModel.Type.ARRAY;
    }

    String elementOfVariableTypeString = variableElement.asType()
                                                        .toString();
    // check for generic ... if there is none, we can't do anything ...
    if (elementOfVariableTypeString.contains("<")) {
      // get flatten super type list and look for Collection class
      Set<TypeMirror> superClasses = this.processorUtils.getFlattenedSupertypeHierarchy(this.processingEnv.getTypeUtils(),
                                                                                        variableElement.asType());
      for (TypeMirror tm : superClasses) {
        if (tm.toString()
              .startsWith(Collection.class.getCanonicalName())) {
          return ValidatorModel.Type.COLLECTION;
        }
      }
    }

    return ValidatorModel.Type.NATIVE;
  }

}
