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
import com.github.nalukit.malio.processor.model.ConstraintModel;
import com.github.nalukit.malio.processor.model.ValidatorModel;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.Malio;
import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.google.auto.service.AutoService;
import com.google.common.base.Stopwatch;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@AutoService(Processor.class)
public class MalioProcessor
    extends AbstractProcessor {

  private ProcessorUtils processorUtils;
  private Stopwatch      stopwatch;


  @SuppressWarnings("rawtypes")
  List<AbstractConstraint> constraints;

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

    MaxValueConstraint maxValueConstraint = new MaxValueConstraint(this.processingEnv, this.processorUtils);
    MinValueConstraint minValueConstraint = new MinValueConstraint(this.processingEnv, this.processorUtils);
    MaxLengthConstraint maxLengthConstraint = new MaxLengthConstraint(this.processingEnv, this.processorUtils);
    MinLengthConstraint minLengthConstraint = new MinLengthConstraint(this.processingEnv, this.processorUtils);
    BlacklistConstraint blacklistConstraint = new BlacklistConstraint(this.processingEnv, this.processorUtils);
    WhitelistConstraint whitelistConstraint = new WhitelistConstraint(this.processingEnv, this.processorUtils);
    NotNullConstraint notNullConstraint = new NotNullConstraint(this.processingEnv, this.processorUtils);
    RegexpConstraint regexpConstraint = new RegexpConstraint(this.processingEnv, this.processorUtils);
    EmailConstraint emailConstraint = new EmailConstraint(this.processingEnv, this.processorUtils);
    MaxDecimalValueConstraint maxDecimalValueConstraint = new MaxDecimalValueConstraint(this.processingEnv, this.processorUtils);
    MinDecimalValueConstraint minDecimalValueConstraint = new MinDecimalValueConstraint(this.processingEnv, this.processorUtils);
    NotBlankConstraint notBlankConstraint = new NotBlankConstraint(this.processingEnv, this.processorUtils);
    NotEmptyConstraint notEmptyConstraint = new NotEmptyConstraint(this.processingEnv, this.processorUtils);
    SizeConstraint sizeConstraint = new SizeConstraint(this.processingEnv, this.processorUtils);

    this.constraints = Arrays.asList(
            notNullConstraint,
            notBlankConstraint, regexpConstraint, emailConstraint,
            maxValueConstraint, minValueConstraint,
            maxLengthConstraint, minLengthConstraint,
            blacklistConstraint, whitelistConstraint,
            maxDecimalValueConstraint, minDecimalValueConstraint,
            notEmptyConstraint, sizeConstraint
            );
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
        processSingleRun(annotations, roundEnv);
      }
    } catch (ProcessorException e) {
      this.processorUtils.createErrorMessage(e.getMessage());
      this.processorUtils.createErrorMessage(e);
      return true;
    }
    return true;
  }

  private void processSingleRun(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) throws ProcessorException {
    if (annotations.size() > 0) {
      for (TypeElement annotation : annotations) {
        if (MalioValidator.class.getCanonicalName().equals(annotation.toString())) {
          processMalioValidator(roundEnv);
        }
      }
    }
  }

  private void processMalioValidator(RoundEnvironment roundEnv) throws ProcessorException {
    for (Element validatorElement : roundEnv.getElementsAnnotatedWith(MalioValidator.class)) {
        processClass(validatorElement);
    }
  }

    private void processClass(Element validatorElement) throws ProcessorException {
        List<ConstraintModel> allConstraintsPerClass = new ArrayList<>();
        Set<TypeMirror> mirrors = this.processorUtils.getFlattenedSupertypeHierarchy(this.processingEnv.getTypeUtils(),
                                                                                     validatorElement.asType());
        for (TypeMirror mirror : mirrors) {
          allConstraintsPerClass.addAll(processVariable(mirror));
        }

        this.createValidator(validatorElement, allConstraintsPerClass);
    }

    private List<ConstraintModel> processVariable(TypeMirror mirror) throws ProcessorException {
        Element element = this.processingEnv.getTypeUtils().asElement(mirror);
        return createConstraints(element);
    }

    private List<ConstraintModel> createConstraints(Element element) throws ProcessorException {
      List<ConstraintModel> constraintsPerVariable = new ArrayList<>();

    for (@SuppressWarnings("rawtypes") AbstractConstraint constraint: this.constraints) {
      for (Object varWithAnnotation : constraint.getVarsWithAnnotation((TypeElement) element)) {
        Element elementWithAnnotation = (Element) varWithAnnotation;
        VariableElement variableElement = (VariableElement) elementWithAnnotation;

        constraint.check(variableElement);
        constraintsPerVariable.add(constraint.createConstraintModel(element, elementWithAnnotation));
        constraint.generate(element, variableElement);
      }
    }
    return constraintsPerVariable;
  }


  private void createValidator(Element validatorElement,
                               List<ConstraintModel> constraintList)
      throws ProcessorException {
    List<ValidatorModel> subValidatorList = ValidatorScanner.builder()
                                                            .elements(this.processingEnv.getElementUtils())
                                                            .types(this.processingEnv.getTypeUtils())
                                                            .validatorElement(validatorElement)
                                                            .processorUtils(this.processorUtils)
                                                            .build()
                                                            .createSubValidatorList();
    ValidatorGenerator.builder()
                      .elements(this.processingEnv.getElementUtils())
                      .filer(this.processingEnv.getFiler())
                      .types(this.processingEnv.getTypeUtils())
                      .constraintList(constraintList)
                      .subValidatorList(subValidatorList)
                      .processorUtils(this.processorUtils)
                      .build()
                      .generate(validatorElement, null);
  }

}
