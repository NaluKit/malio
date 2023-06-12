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

import com.github.nalukit.malio.processor.constraint.AbstractProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.ArrayItemMaxLengthProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.ArrayItemMinLengthProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.ArrayItemNotBlankProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.ArrayItemNotNullProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.ArraySizeProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.BlacklistProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.CollectionItemMaxLengthProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.CollectionItemMinLengthProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.CollectionItemNotBlankProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.CollectionItemNotNullProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.DecimalMaxProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.DecimalMinProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.EmailProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.MaxLengthProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.MaxProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.MinLengthProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.MinProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.NotBlankProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.NotEmptyProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.NotNullProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.NotZeroProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.RegexpProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.SizeProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.UuidProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.WhitelistProcessorConstraint;
import com.github.nalukit.malio.processor.constraint.generator.ValidatorGenerator;
import com.github.nalukit.malio.processor.constraint.scanner.ValidatorScanner;
import com.github.nalukit.malio.processor.exception.UnsupportedTypeException;
import com.github.nalukit.malio.processor.model.ValidatorModel;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.Malio;
import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.ArrayItemMaxLength;
import com.github.nalukit.malio.shared.annotation.field.ArrayItemMinLength;
import com.github.nalukit.malio.shared.annotation.field.ArrayItemNotBlank;
import com.github.nalukit.malio.shared.annotation.field.ArrayItemNotNull;
import com.github.nalukit.malio.shared.annotation.field.ArraySize;
import com.github.nalukit.malio.shared.annotation.field.Blacklist;
import com.github.nalukit.malio.shared.annotation.field.CollectionItemMaxLength;
import com.github.nalukit.malio.shared.annotation.field.CollectionItemMinLength;
import com.github.nalukit.malio.shared.annotation.field.CollectionItemNotBlank;
import com.github.nalukit.malio.shared.annotation.field.CollectionItemNotNull;
import com.github.nalukit.malio.shared.annotation.field.DecimalMax;
import com.github.nalukit.malio.shared.annotation.field.DecimalMin;
import com.github.nalukit.malio.shared.annotation.field.Email;
import com.github.nalukit.malio.shared.annotation.field.Max;
import com.github.nalukit.malio.shared.annotation.field.MaxLength;
import com.github.nalukit.malio.shared.annotation.field.Min;
import com.github.nalukit.malio.shared.annotation.field.MinLength;
import com.github.nalukit.malio.shared.annotation.field.NotBlank;
import com.github.nalukit.malio.shared.annotation.field.NotEmpty;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
import com.github.nalukit.malio.shared.annotation.field.NotZero;
import com.github.nalukit.malio.shared.annotation.field.Regexp;
import com.github.nalukit.malio.shared.annotation.field.Size;
import com.github.nalukit.malio.shared.annotation.field.Uuid;
import com.github.nalukit.malio.shared.annotation.field.Whitelist;
import com.google.auto.service.AutoService;
import com.google.common.base.Stopwatch;
import com.squareup.javapoet.CodeBlock;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@AutoService(Processor.class)
public class MalioProcessor
    extends AbstractProcessor {

  Map<String, AbstractProcessorConstraint<?>> constraints;
  private ProcessorUtils processorUtils;
  private Stopwatch      stopwatch;
  private boolean        generateCheckMethod;
  private boolean        generateValidateMethod;

  public MalioProcessor() {
    super();
    this.constraints = new HashMap<>();
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
    this.processorUtils.createNoteMessage("Malio-Processor started ...");
    String implementationVersion = Malio.getVersion();
    this.processorUtils.createNoteMessage("Malio-Processor version >>" + implementationVersion + "<<");
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnv)
                                        .build();

    this.constraints.put(ArrayItemMaxLength.class.getCanonicalName(),
                         new ArrayItemMaxLengthProcessorConstraint().setUp(this.processingEnv,
                                                                           this.processorUtils));
    this.constraints.put(ArrayItemMinLength.class.getCanonicalName(),
                         new ArrayItemMinLengthProcessorConstraint().setUp(this.processingEnv,
                                                                           this.processorUtils));
    this.constraints.put(ArrayItemNotBlank.class.getCanonicalName(),
                         new ArrayItemNotBlankProcessorConstraint().setUp(this.processingEnv,
                                                                          this.processorUtils));
    this.constraints.put(ArrayItemNotNull.class.getCanonicalName(),
                         new ArrayItemNotNullProcessorConstraint().setUp(this.processingEnv,
                                                                         this.processorUtils));
    this.constraints.put(Blacklist.class.getCanonicalName(),
                         new BlacklistProcessorConstraint().setUp(this.processingEnv,
                                                                  this.processorUtils));
    this.constraints.put(CollectionItemMaxLength.class.getCanonicalName(),
                         new CollectionItemMaxLengthProcessorConstraint().setUp(this.processingEnv,
                                                                                this.processorUtils));
    this.constraints.put(CollectionItemMinLength.class.getCanonicalName(),
                         new CollectionItemMinLengthProcessorConstraint().setUp(this.processingEnv,
                                                                                this.processorUtils));
    this.constraints.put(CollectionItemNotBlank.class.getCanonicalName(),
                         new CollectionItemNotBlankProcessorConstraint().setUp(this.processingEnv,
                                                                               this.processorUtils));
    this.constraints.put(CollectionItemNotNull.class.getCanonicalName(),
                         new CollectionItemNotNullProcessorConstraint().setUp(this.processingEnv,
                                                                              this.processorUtils));
    this.constraints.put(DecimalMax.class.getCanonicalName(),
                         new DecimalMaxProcessorConstraint().setUp(this.processingEnv,
                                                                   this.processorUtils));
    this.constraints.put(Email.class.getCanonicalName(),
                         new EmailProcessorConstraint().setUp(this.processingEnv,
                                                              this.processorUtils));
    this.constraints.put(MaxLength.class.getCanonicalName(),
                         new MaxLengthProcessorConstraint().setUp(this.processingEnv,
                                                                  this.processorUtils));
    this.constraints.put(Max.class.getCanonicalName(),
                         new MaxProcessorConstraint().setUp(this.processingEnv,
                                                            this.processorUtils));
    this.constraints.put(DecimalMin.class.getCanonicalName(),
                         new DecimalMinProcessorConstraint().setUp(this.processingEnv,
                                                                   this.processorUtils));
    this.constraints.put(MinLength.class.getCanonicalName(),
                         new MinLengthProcessorConstraint().setUp(this.processingEnv,
                                                                  this.processorUtils));
    this.constraints.put(Min.class.getCanonicalName(),
                         new MinProcessorConstraint().setUp(this.processingEnv,
                                                            this.processorUtils));
    this.constraints.put(NotBlank.class.getCanonicalName(),
                         new NotBlankProcessorConstraint().setUp(this.processingEnv,
                                                                 this.processorUtils));
    this.constraints.put(NotEmpty.class.getCanonicalName(),
                         new NotEmptyProcessorConstraint().setUp(this.processingEnv,
                                                                 this.processorUtils));
    this.constraints.put(NotNull.class.getCanonicalName(),
                         new NotNullProcessorConstraint().setUp(this.processingEnv,
                                                                this.processorUtils));
    this.constraints.put(Regexp.class.getCanonicalName(),
                         new RegexpProcessorConstraint().setUp(this.processingEnv,
                                                               this.processorUtils));
    this.constraints.put(Size.class.getCanonicalName(),
                         new SizeProcessorConstraint().setUp(this.processingEnv,
                                                             this.processorUtils));
    this.constraints.put(ArraySize.class.getCanonicalName(),
                         new ArraySizeProcessorConstraint().setUp(this.processingEnv,
                                                                  this.processorUtils));
    this.constraints.put(Uuid.class.getCanonicalName(),
                         new UuidProcessorConstraint().setUp(this.processingEnv,
                                                             this.processorUtils));
    this.constraints.put(Whitelist.class.getCanonicalName(),
                         new WhitelistProcessorConstraint().setUp(this.processingEnv,
                                                                  this.processorUtils));
    this.constraints.put(NotZero.class.getCanonicalName(),
                         new NotZeroProcessorConstraint().setUp(this.processingEnv,
                                                                this.processorUtils));
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
      TypeElement typeElement = (TypeElement) validatorElement;
      this.generateCheckMethod    = typeElement.getAnnotation(MalioValidator.class)
                                               .generateCheckMethod();
      this.generateValidateMethod = typeElement.getAnnotation(MalioValidator.class)
                                               .generateValidateMethod();

      processClass(validatorElement);
    }
  }

  private void processClass(Element clazz)
      throws ProcessorException {
    MalioValidatorGenerator malioValidatorGenerator = new MalioValidatorGenerator(this.processingEnv,
                                                                                  processorUtils,
                                                                                  clazz,
                                                                                  this.generateCheckMethod,
                                                                                  this.generateValidateMethod);
    this.processVariable(clazz.asType(),
                         malioValidatorGenerator);
    this.createSubAndSuperValidators(clazz,
                                     malioValidatorGenerator);
    malioValidatorGenerator.writeFile(this.generateCheckMethod,
                                      this.generateValidateMethod);
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
      this.createConstraints(field,
                             malioValidatorGenerator);
    }
  }

  private void createConstraints(VariableElement field,
                                 MalioValidatorGenerator malioValidatorGenerator)
      throws ProcessorException {
    for (AnnotationMirror annotation : field.getAnnotationMirrors()) {
      AbstractProcessorConstraint<?> constraint = this.constraints.get(annotation.getAnnotationType()
                                                                                 .toString());
      // c heck whether he annotation is related to Malio or not ...
      if (Objects.isNull(constraint)) {
        continue;
      }
      if (constraint.isTargetingNative()) {
        constraint.checkDataType(field);
        this.createCodeForNativeConstraint(field,
                                           malioValidatorGenerator,
                                           constraint);
      }
      if (constraint.isTargetingArrayItem()) {
        if (!field.asType()
                  .getKind()
                  .equals(TypeKind.ARRAY)) {
          throw new UnsupportedTypeException("Constraint >>" + annotation + "<< not supported for >>" + field.asType() + "<<");
        }
        constraint.checkDataTypeArrayItem(field);
        this.createCodeForArrayConstraint(field,
                                          malioValidatorGenerator,
                                          constraint);
      }
      if (constraint.isTargetingListItem()) {
        ValidatorModel.ComponentType componentType = this.getComponentTypeFromVariableElement(field);
        if (ValidatorModel.ComponentType.COLLECTION != componentType) {
          throw new UnsupportedTypeException("Constraint >>" + annotation + "<< not supported for >>" + field.asType() + "<<");
        }
        constraint.checkDataTypeCollectionItem(field);
        this.createCodeForCollectionConstraint(field,
                                               malioValidatorGenerator,
                                               constraint);
      }
    }
  }

  private void createCodeForNativeConstraint(VariableElement field,
                                             MalioValidatorGenerator malioValidatorGenerator,
                                             AbstractProcessorConstraint<?> constraint)
      throws ProcessorException {
    if (this.generateCheckMethod) {
      CodeBlock checkBlock = constraint.generateCheckNative(field,
                                                            field);
      malioValidatorGenerator.appendCheckStatement(checkBlock);
    }
    if (this.generateValidateMethod) {
      CodeBlock validBlock = constraint.generateValidNative(field,
                                                            field);
      malioValidatorGenerator.appendValidStatement(validBlock);
    }
  }

  private void createCodeForArrayConstraint(VariableElement field,
                                            MalioValidatorGenerator malioValidatorGenerator,
                                            AbstractProcessorConstraint<?> constraint)
      throws ProcessorException {
    malioValidatorGenerator.appendBeginControlFlowArray(field,
                                                        this.generateCheckMethod,
                                                        this.generateValidateMethod);
    if (this.generateCheckMethod) {
      CodeBlock checkBlock = constraint.generateCheckArray(field,
                                                           field);
      malioValidatorGenerator.appendCheckStatement(checkBlock);
    }
    if (this.generateValidateMethod) {
      CodeBlock validBlock = constraint.generateValidArray(field,
                                                           field);
      malioValidatorGenerator.appendValidStatement(validBlock);
    }
    malioValidatorGenerator.appendEndControlFlow(this.generateCheckMethod,
                                                 this.generateValidateMethod);
  }

  private void createCodeForCollectionConstraint(VariableElement field,
                                                 MalioValidatorGenerator malioValidatorGenerator,
                                                 AbstractProcessorConstraint<?> constraint)
      throws ProcessorException {
    malioValidatorGenerator.appendBeginControlFlowCollection(field,
                                                             this.generateCheckMethod,
                                                             this.generateValidateMethod);
    if (this.generateCheckMethod) {
      CodeBlock checkBlock = constraint.generateCheckCollection(field,
                                                                field);
      malioValidatorGenerator.appendCheckStatement(checkBlock);
    }
    if (this.generateValidateMethod) {
      CodeBlock validBlock = constraint.generateValidCollection(field,
                                                                field);
      malioValidatorGenerator.appendValidStatement(validBlock);
    }
    malioValidatorGenerator.appendEndControlFlow(this.generateCheckMethod,
                                                 this.generateValidateMethod);
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
    if (this.generateCheckMethod) {
      generator.appendSuperAndSubValidatorsCheck();
    }
    if (this.generateValidateMethod) {
      generator.appendSuperAndSubValidatorsValid();
    }
  }

  private ValidatorModel.ComponentType getComponentTypeFromVariableElement(VariableElement variableElement) {
    if (variableElement.asType()
                       .getKind()
                       .equals(TypeKind.ARRAY)) {
      return ValidatorModel.ComponentType.ARRAY;
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
          return ValidatorModel.ComponentType.COLLECTION;
        }
      }
    }

    return ValidatorModel.ComponentType.NATIVE;
  }

}
