package com.github.nalukit.malio.processor;

import com.github.nalukit.malio.processor.constraints.AbstractConstraint;
import com.github.nalukit.malio.processor.constraints.MaxValueConstraint;
import com.github.nalukit.malio.processor.constraints.generator.*;
import com.github.nalukit.malio.processor.constraints.scanner.*;
import com.github.nalukit.malio.processor.model.ConstraintModel;
import com.github.nalukit.malio.processor.model.ValidatorModel;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.Malio;
import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.*;
import com.google.auto.service.AutoService;
import com.google.common.base.Stopwatch;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
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

        // handle malio annotations ...
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


      /* experimental */
//    for (AbstractConstraint constraint: this.constraints) {
//      for (Object uncElement : constraint.getDiesDas((TypeElement) element)) {
//        Element variableElement = (Element) uncElement;
//
//        //constraint.scanner.;
//        ConstraintModel constraintModel = ConstraintBlacklistScanner.builder()
//                .elements(this.processingEnv.getElementUtils())
//                .types(this.processingEnv.getTypeUtils())
//                .validatorElement(element)
//                .variableElement(variableElement)
//                .processorUtils(this.processorUtils)
//                .build()
//                .createConstraintModel();
//        constraintsPerVariable.add(constraintModel);
//
//
//        ConstraintBlacklistGenerator.builder()
//                .elements(this.processingEnv.getElementUtils())
//                .filer(this.processingEnv.getFiler())
//                .types(this.processingEnv.getTypeUtils())
//                .validatorElement(element)
//                .variableElement(variableElement)
//                .processorUtils(this.processorUtils)
//                .build()
//                .generate();
//      }
//    }
      /* experimental */

    this.createConstraintNotNull(element, constraintsPerVariable);
    this.createConstraintMaxLength(element, constraintsPerVariable);
    this.createConstraintMinLength(element, constraintsPerVariable);
    this.createConstraintMaxValue(element, constraintsPerVariable);
    this.createConstraintMinValue(element, constraintsPerVariable);
    this.createConstraintRegexp(element, constraintsPerVariable);
    this.createConstraintWhitelist(element, constraintsPerVariable);
    this.createConstraintBlacklist(element, constraintsPerVariable);
    // TODO add more constraints
    return constraintsPerVariable;
  }

  private void createConstraintBlacklist(Element element,
                                         List<ConstraintModel> constraintList)
          throws ProcessorException {
    for (Element variableElement : this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnv,
            (TypeElement) element,
            Blacklist.class)) {
      ConstraintModel constraintModel = ConstraintBlacklistScanner.builder()
              .elements(this.processingEnv.getElementUtils())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .createConstraintModel();
      ConstraintBlacklistGenerator.builder()
              .elements(this.processingEnv.getElementUtils())
              .filer(this.processingEnv.getFiler())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .generate();
      constraintList.add(constraintModel);
    }
  }

  private void createConstraintWhitelist(Element element,
                                        List<ConstraintModel> constraintList)
          throws ProcessorException {
    for (Element variableElement : this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnv,
            (TypeElement) element,
            Whitelist.class)) {
      ConstraintModel constraintModel = ConstraintWhitelistScanner.builder()
              .elements(this.processingEnv.getElementUtils())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .createConstraintModel();
      ConstraintWhitelistGenerator.builder()
              .elements(this.processingEnv.getElementUtils())
              .filer(this.processingEnv.getFiler())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .generate();
      constraintList.add(constraintModel);
    }
  }
  private void createConstraintMaxValue(Element element,
                                         List<ConstraintModel> constraintList)
          throws ProcessorException {
    List<Element> varsWithAnnotation = this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnv,
            (TypeElement) element,
            MaxValue.class);
    for (Element variableElement : varsWithAnnotation) {
      ConstraintModel constraintModel = ConstraintMaxValueScanner.builder()
              .elements(this.processingEnv.getElementUtils())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .createConstraintModel();
      ConstraintMaxValueGenerator.builder()
              .elements(this.processingEnv.getElementUtils())
              .filer(this.processingEnv.getFiler())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .generate();
      constraintList.add(constraintModel);
    }
  }

  private void createConstraintRegexp(Element element,
                                        List<ConstraintModel> constraintList)
          throws ProcessorException {
    for (Element variableElement : this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnv,
            (TypeElement) element,
            Regexp.class)) {
      ConstraintModel constraintModel = ConstraintRegexpScanner.builder()
              .elements(this.processingEnv.getElementUtils())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .createConstraintModel();
      ConstraintRegexpGenerator.builder()
              .elements(this.processingEnv.getElementUtils())
              .filer(this.processingEnv.getFiler())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .generate();
      constraintList.add(constraintModel);
    }
  }

  private void createConstraintMinValue(Element element,
                                        List<ConstraintModel> constraintList)
          throws ProcessorException {
    for (Element variableElement : this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnv,
            (TypeElement) element,
            MinValue.class)) {
      ConstraintModel constraintModel = ConstraintMinValueScanner.builder()
              .elements(this.processingEnv.getElementUtils())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .createConstraintModel();
      ConstraintMinValueGenerator.builder()
              .elements(this.processingEnv.getElementUtils())
              .filer(this.processingEnv.getFiler())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .generate();
      constraintList.add(constraintModel);
    }
  }

  private void createConstraintMaxLength(Element element,
                                         List<ConstraintModel> constraintList)
      throws ProcessorException {
    for (Element variableElement : this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnv,
                                                                                                (TypeElement) element,
                                                                                                MaxLength.class)) {
      ConstraintModel constraintModel = ConstraintMaxLengthScanner.builder()
                                                                  .elements(this.processingEnv.getElementUtils())
                                                                  .types(this.processingEnv.getTypeUtils())
                                                                  .validatorElement(element)
                                                                  .variableElement(variableElement)
                                                                  .processorUtils(this.processorUtils)
                                                                  .build()
                                                                  .createConstraintModel();
      ConstraintMaxLengthGenerator.builder()
                                  .elements(this.processingEnv.getElementUtils())
                                  .filer(this.processingEnv.getFiler())
                                  .types(this.processingEnv.getTypeUtils())
                                  .validatorElement(element)
                                  .variableElement(variableElement)
                                  .processorUtils(this.processorUtils)
                                  .build()
                                  .generate();
      constraintList.add(constraintModel);
    }
  }

  private void createConstraintMinLength(Element element,
                                         List<ConstraintModel> constraintList)
          throws ProcessorException {
    for (Element variableElement : this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnv,
            (TypeElement) element,
            MinLength.class)) {
      ConstraintModel constraintModel = ConstraintMinLengthScanner.builder()
              .elements(this.processingEnv.getElementUtils())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .createConstraintModel();
      ConstraintMinLengthGenerator.builder()
              .elements(this.processingEnv.getElementUtils())
              .filer(this.processingEnv.getFiler())
              .types(this.processingEnv.getTypeUtils())
              .validatorElement(element)
              .variableElement(variableElement)
              .processorUtils(this.processorUtils)
              .build()
              .generate();
      constraintList.add(constraintModel);
    }
  }

  private void createConstraintNotNull(Element element,
                                       List<ConstraintModel> constraintList)
      throws ProcessorException {
    for (Element variableElement : this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnv,
                                                                                                (TypeElement) element,
                                                                                                NotNull.class)) {
      ConstraintModel constraintModel = ConstraintNotNullScanner.builder()
                                                                .elements(this.processingEnv.getElementUtils())
                                                                .types(this.processingEnv.getTypeUtils())
                                                                .validatorElement(element)
                                                                .variableElement(variableElement)
                                                                .processorUtils(this.processorUtils)
                                                                .build()
                                                                .createConstraintModel();
      ConstraintNotNullGenerator.builder()
                                .elements(this.processingEnv.getElementUtils())
                                .filer(this.processingEnv.getFiler())
                                .types(this.processingEnv.getTypeUtils())
                                .validatorElement(element)
                                .variableElement(variableElement)
                                .processorUtils(this.processorUtils)
                                .build()
                                .generate();
      constraintList.add(constraintModel);
    }
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
                      .validatorElement(validatorElement)
                      .processorUtils(this.processorUtils)
                      .build()
                      .generate();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(processingEnv)
                                        .build();
    this.constraints = Arrays.asList(new MaxValueConstraint(this.processingEnv, this.processorUtils));
  }

}
