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

import com.github.nalukit.malio.processor.constraints.*;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
    MaxDecimalValueConstraint maxDecimalValueConstraint = new MaxDecimalValueConstraint(this.processingEnv,
                                                                                        this.processorUtils);
    MaxLengthConstraint maxLengthConstraint = new MaxLengthConstraint(this.processingEnv,
                                                                      this.processorUtils);
    MaxValueConstraint maxValueConstraint = new MaxValueConstraint(this.processingEnv,
                                                                   this.processorUtils);
    MinDecimalValueConstraint minDecimalValueConstraint = new MinDecimalValueConstraint(this.processingEnv,
                                                                                        this.processorUtils);
    MinLengthConstraint minLengthConstraint = new MinLengthConstraint(this.processingEnv,
                                                                      this.processorUtils);
    MinValueConstraint minValueConstraint = new MinValueConstraint(this.processingEnv,
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

    this.constraints = Arrays.asList(notNullConstraint,
                                     notBlankConstraint,
                                     regexpConstraint,
                                     emailConstraint,
                                     uuidConstraint,
                                     maxValueConstraint,
                                     minValueConstraint,
                                     maxLengthConstraint,
                                     minLengthConstraint,
                                     blacklistConstraint,
                                     whitelistConstraint,
                                     maxDecimalValueConstraint,
                                     minDecimalValueConstraint,
                                     notEmptyConstraint,
                                     sizeConstraint,
                arraySizeConstraint);
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
    MalioValidatorGenerator malioValidatorGenerator = new MalioValidatorGenerator(this.processingEnv, processorUtils, clazz);
    this.processVariable(clazz.asType(), malioValidatorGenerator);
    this.createSubAndSuperValidators(clazz, malioValidatorGenerator);
    malioValidatorGenerator.writeFile();
  }

  private void processVariable(TypeMirror mirror, MalioValidatorGenerator malioValidatorGenerator)
      throws ProcessorException {
    Element element = this.processingEnv.getTypeUtils()
                                        .asElement(mirror);
    createConstraints(element, malioValidatorGenerator);
  }

  private void createConstraints(Element clazz, MalioValidatorGenerator malioValidatorGenerator)
      throws ProcessorException {

    for (
        @SuppressWarnings("rawtypes") AbstractConstraint constraint : this.constraints) {
      for (Object varWithAnnotation : constraint.getVarsWithAnnotation((TypeElement) clazz)) {
        Element         elementWithAnnotation = (Element) varWithAnnotation;
        VariableElement field       = (VariableElement) elementWithAnnotation;

        constraint.check(field);
        CodeBlock checkBlock = constraint.generateCheck(clazz, field);
        CodeBlock validBlock = constraint.generateValid(clazz, field);

        malioValidatorGenerator.appendCheckStatement(checkBlock);
        malioValidatorGenerator.appendValidStatement(validBlock);
      }
    }

  }

  private void createSubAndSuperValidators(Element validatorElement, MalioValidatorGenerator malioValidatorGenerator)
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

}
