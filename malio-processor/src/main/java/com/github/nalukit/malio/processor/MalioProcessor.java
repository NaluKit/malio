package com.github.nalukit.malio.processor;

import com.github.nalukit.malio.processor.model.ConstraintModel;
import com.github.nalukit.malio.processor.model.ValidatorModel;
import com.github.nalukit.malio.processor.model.ValidatorType;
import com.github.nalukit.malio.processor.util.BuildWithMalioCommentProvider;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.Malio;
import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
import com.github.nalukit.malio.shared.internal.constraints.AbstractNotNullConstraint;
import com.github.nalukit.malio.shared.internal.validator.AbstractValidator;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.google.auto.service.AutoService;
import com.google.common.base.Stopwatch;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@AutoService(Processor.class)
public class MalioProcessor
    extends AbstractProcessor {

  private final static String MALIO_VALIDATOR_IMPL_NAME          = "MalioValidator";
  private final static String MALIO_VALIDATOR_NOT_NULL_IMPL_NAME = "MalioConstraintNotNull";

  //
  //  private final static String APPLICATION_PROPERTIES = "nalu.properties";

  private ProcessorUtils processorUtils;
  private Stopwatch      stopwatch;
  //  private MetaModel      metaModel = new MetaModel();

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
        //        if (!roundEnv.errorRaised()) {
        //          this.validate(roundEnv);
        //          this.generateLastRound();
        //          this.store(metaModel);
        //        }
        this.processorUtils.createNoteMessage("Malio-Processor finished ... processing takes: " +
                                              this.stopwatch.stop()
                                                            .toString());
      } else {
        if (annotations.size() > 0) {
          for (TypeElement annotation : annotations) {
            if (MalioValidator.class.getCanonicalName()
                                    .equals(annotation.toString())) {
              for (Element validatorElement : roundEnv.getElementsAnnotatedWith(MalioValidator.class)) {
                List<ConstraintModel> constraintsList = new ArrayList<>();
                List<ValidatorModel>  validatorList   = new ArrayList<>();
                Set<TypeMirror> mirrors = this.processorUtils.getFlattenedSupertypeHierarchy(this.processingEnv.getTypeUtils(),
                                                                                             validatorElement.asType());
                for (TypeMirror mirror : mirrors) {
                  Element element = this.processingEnv.getTypeUtils()
                                                      .asElement(mirror);

                  this.createConstraintNotNull(element,
                                               constraintsList,
                                               validatorList);

                  // TODO add more constraints
                  // TODO add more constraints
                  // TODO add more constraints
                  // TODO add more constraints
                  // TODO add more constraints
                  // TODO add more constraints
                  // TODO add more constraints
                  // TODO add more constraints
                  // TODO add more constraints

                }
                this.generateValidator(validatorElement,
                                       constraintsList,
                                       validatorList);
              }
            }
          }
        }
      }
    } catch (ProcessorException e) {
      this.processorUtils.createErrorMessage(e.getMessage());
      return true;
    }
    return true;
  }

  private void generateValidator(Element validatorElement,
                                 List<ConstraintModel> constraintsList,
                                 List<ValidatorModel> validatorList)
      throws ProcessorException {
    TypeSpec.Builder typeSpec = createValidatorTypeSpec(validatorElement);

    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addModifiers(Modifier.PUBLIC)
                                 .addStatement("super()")
                                 .build());

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

    MethodSpec.Builder checkMethodBuilder = MethodSpec.methodBuilder("check")
                                                      .addModifiers(Modifier.PUBLIC)
                                                      .addParameter(ParameterSpec.builder(ClassName.get(validatorElement.asType()),
                                                                                          "bean")
                                                                                 .build())
                                                      .returns(void.class)
                                                      .addException(ClassName.get(MalioValidationException.class));

    int checkValidatorCounter = 1;
    for (ConstraintModel model : constraintsList) {
      String variableName = "val" + this.getStringFromInt(checkValidatorCounter);
      String constraintClassName = this.createConstraintClassName(model.getSimpleClassName(),
                                                                  model.getFieldName(),
                                                                  model.getPostFix());
      checkMethodBuilder.addStatement("$T " + variableName + " =  new $T()",
                                      ClassName.get(model.getPackageName(),
                                                    constraintClassName),
                                      ClassName.get(model.getPackageName(),
                                                    constraintClassName));
      checkMethodBuilder.addStatement(variableName +
                                      ".check(bean." +
                                      this.processorUtils.createGetMethodName(model.getFieldName()) +
                                      "())");
      checkValidatorCounter++;
    }
    for (ValidatorModel model : validatorList) {
      String vaidatorClassName = model.getSimpleClassName() + model.getPostFix();
      checkMethodBuilder.addStatement("$T.INSTANCE.check(bean." +
                                      this.processorUtils.createGetMethodName(model.getFieldName()) +
                                      "())",
                                      ClassName.get(model.getPackageName(),
                                                    vaidatorClassName));
    }
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
    int validateValidatorCounter = 1;
    for (ConstraintModel model : constraintsList) {
      String variableName = "val" + this.getStringFromInt(validateValidatorCounter);
      String constraintClassName = this.createConstraintClassName(model.getSimpleClassName(),
                                                                  model.getFieldName(),
                                                                  model.getPostFix());
      validMethodTwoParameterBuilder.addStatement("$T " + variableName + " =  new $T()",
                                                  ClassName.get(model.getPackageName(),
                                                                constraintClassName),
                                                  ClassName.get(model.getPackageName(),
                                                                constraintClassName));
      validMethodTwoParameterBuilder.addStatement(variableName +
                                                  ".isValid(bean." +
                                                  this.processorUtils.createGetMethodName(model.getFieldName()) +
                                                  "(), validationResult)",
                                                  ClassName.get(ValidationResult.class));
      validateValidatorCounter++;
    }
    for (ValidatorModel model : validatorList) {
      String vaidatorClassName = model.getSimpleClassName() + model.getPostFix();
      validMethodTwoParameterBuilder.addStatement("validationResult = $T.INSTANCE.validate(bean." +
                                                  this.processorUtils.createGetMethodName(model.getFieldName()) +
                                                  "(), validationResult)",
                                                  ClassName.get(model.getPackageName(),
                                                                vaidatorClassName));
    }
    validMethodTwoParameterBuilder.addStatement("return validationResult");
    typeSpec.addMethod(validMethodTwoParameterBuilder.build());

    this.writeFile(validatorElement,
                   MalioProcessor.MALIO_VALIDATOR_IMPL_NAME,
                   typeSpec);
  }

  private String getStringFromInt(int value) {
    if (value < 1) {
      return "00000";
    } else  if (value < 10) {
      return "0000" + value;
    } else  if (value < 100) {
      return "000"+ value;
    } else  if (value < 1000) {
      return "00" + value;
    } else  if (value < 10000) {
      return "0" + value;
    } else {
      return String.valueOf(value);
    }
  }

  private void createConstraintNotNull(Element element,
                                       List<ConstraintModel> constraintsList,
                                       List<ValidatorModel> validatorList)
      throws ProcessorException {
    for (Element fieldElement : this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnv,
                                                                                             (TypeElement) element,
                                                                                             NotNull.class)) {
      VariableElement variableElement = (VariableElement) fieldElement;
      this.generateNotNullConstraint(element,
                                     constraintsList,
                                     variableElement);
      TypeElement elementOfVariableType = (TypeElement) this.processingEnv.getTypeUtils()
                                                                          .asElement(variableElement.asType());
      if (elementOfVariableType.getAnnotation(MalioValidator.class) != null) {
        this.addValidatorToValidatorGenerationList(validatorList,
                                                   variableElement,
                                                   elementOfVariableType);
      }
    }
  }

  private void addValidatorToValidatorGenerationList(List<ValidatorModel> validatorList,
                                                     VariableElement variableElement,
                                                     TypeElement elementOfVariableType) {
    boolean found = validatorList.stream()
                                 .anyMatch(model -> model.getPackageName()
                                                         .equals(this.processorUtils.getPackageAsString(elementOfVariableType)) &&
                                                    model.getSimpleClassName()
                                                         .equals(elementOfVariableType.getSimpleName()
                                                                                      .toString()) &&
                                                    model.getPostFix()
                                                         .equals(MalioProcessor.MALIO_VALIDATOR_IMPL_NAME));
    if (!found) {
      validatorList.add(new ValidatorModel(this.processorUtils.getPackageAsString(elementOfVariableType),
                                           elementOfVariableType.getSimpleName()
                                                                .toString(),
                                           variableElement.toString(),
                                           MalioProcessor.MALIO_VALIDATOR_IMPL_NAME));
    }
  }

  private void generateNotNullConstraint(Element validatorElement,
                                         List<ConstraintModel> constraintsList,
                                         VariableElement variableElement)
      throws ProcessorException {
    TypeSpec.Builder typeSpec = createConstraintTypeSpec(validatorElement,
                                                         variableElement);
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addModifiers(Modifier.PUBLIC)
                                 .addStatement("super($S, $S, $S)",
                                               this.processorUtils.getPackage(variableElement),
                                               this.processorUtils.setFirstCharacterToUpperCase(variableElement.getEnclosingElement()
                                                                                                               .getSimpleName()
                                                                                                               .toString()),
                                               variableElement.getSimpleName()
                                                              .toString())

                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("getErrorMessage")
                                 .addModifiers(Modifier.PROTECTED)
                                 .addAnnotation(ClassName.get(Override.class))
                                 .returns(ClassName.get(String.class))
                                 .addStatement("return \"noch mit error messages aus Properties ersetzen (wegen locale und so) ....\"")
                                 .build());
    this.writeFile(variableElement,
                   MalioProcessor.MALIO_VALIDATOR_NOT_NULL_IMPL_NAME,
                   typeSpec);
    constraintsList.add(new ConstraintModel(this.processorUtils.getPackageAsString(validatorElement),
                                            validatorElement.getSimpleName()
                                                            .toString(),
                                            variableElement.toString(),
                                            MalioProcessor.MALIO_VALIDATOR_NOT_NULL_IMPL_NAME,
                                            ValidatorType.NOT_NULL_VALIDATOR));
  }

  private TypeSpec.Builder createConstraintTypeSpec(Element validatorElement,
                                                    VariableElement variableElement) {
    System.out.println(variableElement.asType());
    return TypeSpec.classBuilder(this.createConstraintClassName(validatorElement.getSimpleName()
                                                                                .toString(),
                                                                variableElement.getSimpleName()
                                                                               .toString(),
                                                                MalioProcessor.MALIO_VALIDATOR_NOT_NULL_IMPL_NAME))
                   .addJavadoc(BuildWithMalioCommentProvider.INSTANCE.getGeneratedComment())
                   .superclass(ParameterizedTypeName.get(ClassName.get(AbstractNotNullConstraint.class),
                                                         ClassName.get(variableElement.asType())))
                   .addModifiers(Modifier.PUBLIC,
                                 Modifier.FINAL);
  }

  private TypeSpec.Builder createValidatorTypeSpec(Element validatorElement) {
    return TypeSpec.classBuilder(this.createValidatorClassName(validatorElement.getSimpleName()
                                                                               .toString()))
                   .addJavadoc(BuildWithMalioCommentProvider.INSTANCE.getGeneratedComment())
                   .superclass(ClassName.get(AbstractValidator.class))
                   .addModifiers(Modifier.PUBLIC,
                                 Modifier.FINAL);
  }

  //  private void validate(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    if (!isNull(this.metaModel)) {
  //      ConsistenceValidator.builder()
  //                          .roundEnvironment(roundEnv)
  //                          .processingEnvironment(this.processingEnv)
  //                          .metaModel(this.metaModel)
  //                          .build()
  //                          .validate();
  //    }
  //  }
  //
  //  private void generateLastRound()
  //      throws ProcessorException {
  //    if (!isNull(this.metaModel)) {
  //      ApplicationGenerator.builder()
  //                          .processingEnvironment(this.processingEnv)
  //                          .build()
  //                          .generate(this.metaModel);
  //      // check if moduleModel is not null!
  //      // if moduleModel is null, we have nothing to do here,
  //      // otherwise we need to generate a module-Impl class
  //      if (!Objects.isNull(metaModel.getModuleModel())) {
  //        ModuleGenerator.builder()
  //                       .processingEnvironment(processingEnv)
  //                       .metaModel(this.metaModel)
  //                       .build()
  //                       .generate();
  //      }
  //    }
  //  }
  //
  //  private void store(MetaModel model)
  //      throws ProcessorException {
  //    Gson gson = new Gson();
  //    try {
  //      FileObject fileObject = processingEnv.getFiler()
  //                                           .createResource(StandardLocation.CLASS_OUTPUT,
  //                                                           "",
  //                                                           this.createRelativeFileName());
  //      PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(fileObject.openOutputStream(),
  //                                                                       UTF_8));
  //      printWriter.print(gson.toJson(model));
  //      printWriter.flush();
  //      printWriter.close();
  //    } catch (IOException e) {
  //      throw new ProcessorException("NaluProcessor: Unable to write file: >>" +
  //                                   this.createRelativeFileName() +
  //                                   "<< -> exception: " +
  //                                   e.getMessage());
  //    }
  //  }
  //
  //  private void handleApplicationAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    for (Element applicationElement : roundEnv.getElementsAnnotatedWith(Application.class)) {
  //      // validate application element
  //      ApplicationAnnotationValidator.builder()
  //                                    .processingEnvironment(processingEnv)
  //                                    .applicationElement(applicationElement)
  //                                    .build()
  //                                    .validate();
  //      // scan application element
  //      ApplicationAnnotationScanner.builder()
  //                                  .processingEnvironment(processingEnv)
  //                                  .applicationElement(applicationElement)
  //                                  .metaModel(metaModel)
  //                                  .build()
  //                                  .scan();
  //    }
  //  }
  //
  //  private void handleBlockControllerAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    List<BlockControllerModel> blockControllerModels = new ArrayList<>();
  //    for (Element blockControllerElement : roundEnv.getElementsAnnotatedWith(BlockController.class)) {
  //      // validate
  //      BlockControllerAnnotationValidator.builder()
  //                                        .processingEnvironment(processingEnv)
  //                                        .blockControllerElement(blockControllerElement)
  //                                        .build()
  //                                        .validate();
  //      // create PopUpControllerModel
  //      BlockControllerModel blockControllerModel = BlockControllerAnnotationScanner.builder()
  //                                                                                  .processingEnvironment(processingEnv)
  //                                                                                  .metaModel(this.metaModel)
  //                                                                                  .blockControllerElement(blockControllerElement)
  //                                                                                  .build()
  //                                                                                  .scan(roundEnv);
  //      // generate BlockControllerCreator
  //      BlockControllerCreatorGenerator.builder()
  //                                     .processingEnvironment(processingEnv)
  //                                     .metaModel(this.metaModel)
  //                                     .blockControllerModel(blockControllerModel)
  //                                     .build()
  //                                     .generate();
  //      blockControllerModels.add(blockControllerModel);
  //    }
  //    // check, if the one of the popUpController in the list is already
  //    // added to the the meta model
  //    //
  //    // in case it is, remove it.
  //    blockControllerModels.forEach(model -> {
  //      Optional<BlockControllerModel> optional = this.metaModel.getBlockControllers()
  //                                                              .stream()
  //                                                              .filter(s -> model.getController()
  //                                                                                .getClassName()
  //                                                                                .equals(s.getController()
  //                                                                                         .getClassName()))
  //                                                              .findFirst();
  //      optional.ifPresent(optionalBlockControllerModel -> this.metaModel.getBlockControllers()
  //                                                                       .remove(optionalBlockControllerModel));
  //    });
  //    // save data in metaModel
  //    this.metaModel.getBlockControllers()
  //                  .addAll(blockControllerModels);
  //  }
  //
  //  private void handleCompositeControllerAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    for (Element compositeElement : roundEnv.getElementsAnnotatedWith(CompositeController.class)) {
  //      // validate handler element
  //      CompositeControllerAnnotationValidator.builder()
  //                                            .processingEnvironment(processingEnv)
  //                                            .roundEnvironment(roundEnv)
  //                                            .compositeElement(compositeElement)
  //                                            .build()
  //                                            .validate();
  //      // scan controller element
  //      CompositeModel compositeModel = CompositeControllerAnnotationScanner.builder()
  //                                                                          .processingEnvironment(processingEnv)
  //                                                                          .metaModel(this.metaModel)
  //                                                                          .compositeElement(compositeElement)
  //                                                                          .build()
  //                                                                          .scan(roundEnv);
  //
  //      // create the ControllerCreator
  //      CompositeCreatorGenerator.builder()
  //                               .metaModel(this.metaModel)
  //                               .processingEnvironment(processingEnv)
  //                               .compositeModel(compositeModel)
  //                               .build()
  //                               .generate();
  //    }
  //  }
  //
  //  private void handleCompositesAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    for (Element element : roundEnv.getElementsAnnotatedWith(Composites.class)) {
  //      // validate annodation
  //      CompositesAnnotationValidator.builder()
  //                                   .processingEnvironment(processingEnv)
  //                                   .roundEnvironment(roundEnv)
  //                                   .element(element)
  //                                   .build()
  //                                   .validate();
  //    }
  //  }
  //
  //  private void handleControllerAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    for (Element controllerElement : roundEnv.getElementsAnnotatedWith(Controller.class)) {
  //      // validate handler element
  //      ControllerAnnotationValidator.builder()
  //                                   .processingEnvironment(processingEnv)
  //                                   .roundEnvironment(roundEnv)
  //                                   .controllerElement(controllerElement)
  //                                   .build()
  //                                   .validate();
  //      // scan controller element
  //      ControllerModel controllerModel = ControllerAnnotationScanner.builder()
  //                                                                   .processingEnvironment(processingEnv)
  //                                                                   .metaModel(this.metaModel)
  //                                                                   .controllerElement(controllerElement)
  //                                                                   .build()
  //                                                                   .scan(roundEnv);
  //
  //      // Composites-Annotation in controller
  //      controllerModel = ControllerCompositesAnnotationScanner.builder()
  //                                                             .processingEnvironment(processingEnv)
  //                                                             .controllerModel(controllerModel)
  //                                                             .controllerElement(controllerElement)
  //                                                             .build()
  //                                                             .scan(roundEnv);
  //      // create the ControllerCreator
  //      ControllerCreatorGenerator.builder()
  //                                .metaModel(this.metaModel)
  //                                .processingEnvironment(processingEnv)
  //                                .controllerModel(controllerModel)
  //                                .build()
  //                                .generate();
  //      // check, if the controller is already
  //      // added to the the meta model
  //      //
  //      // in case it is, remove it.
  //      final String controllerClassname = controllerModel.getController()
  //                                                        .getClassName();
  //      Optional<ControllerModel> optional = this.metaModel.getControllers()
  //                                                         .stream()
  //                                                         .filter(s -> controllerClassname.equals(s.getController()
  //                                                                                                  .getClassName()))
  //                                                         .findFirst();
  //      optional.ifPresent(optionalControllerModel -> this.metaModel.getControllers()
  //                                                                  .remove(optionalControllerModel));
  //      // save controller data in metaModel
  //      this.metaModel.getControllers()
  //                    .add(controllerModel);
  //    }
  //  }
  //
  //  private void handleErrorPopUpControllerAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    Set<? extends Element> listOfAnnotatedElements = roundEnv.getElementsAnnotatedWith(ErrorPopUpController.class);
  //    if (listOfAnnotatedElements.size() > 1) {
  //      throw new ProcessorException("Nalu-Processor: more than one class is annotated with @ErrorPopUpController");
  //    }
  //    List<ErrorPopUpControllerModel> errorPopUpControllerModels = new ArrayList<>();
  //    for (Element errorPopUpControllerElement : roundEnv.getElementsAnnotatedWith(ErrorPopUpController.class)) {
  //      // validate
  //      ErrorPopUpControllerAnnotationValidator.builder()
  //                                             .processingEnvironment(processingEnv)
  //                                             .errorPopUpControllerElement(errorPopUpControllerElement)
  //                                             .build()
  //                                             .validate();
  //      // create PopUpControllerModel
  //      ErrorPopUpControllerModel errorPopUpControllerModel = ErrorPopUpControllerAnnotationScanner.builder()
  //                                                                                                 .processingEnvironment(processingEnv)
  //                                                                                                 .metaModel(this.metaModel)
  //                                                                                                 .popUpControllerElement(errorPopUpControllerElement)
  //                                                                                                 .build()
  //                                                                                                 .scan(roundEnv);
  //      errorPopUpControllerModels.add(errorPopUpControllerModel);
  //    }
  //    // save data in metaModel
  //    if (errorPopUpControllerModels.size() > 0) {
  //      this.metaModel.setErrorPopUpController(errorPopUpControllerModels.get(0));
  //    }
  //  }
  //
  //  private void handleFiltersAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    for (Element filtersElement : roundEnv.getElementsAnnotatedWith(Filters.class)) {
  //      // validate filter element
  //      FiltersAnnotationValidator.builder()
  //                                .roundEnvironment(roundEnv)
  //                                .processingEnvironment(processingEnv)
  //                                .build()
  //                                .validate(filtersElement);
  //      // scan filter element
  //      List<FilterModel> filterModels = FiltersAnnotationScanner.builder()
  //                                                               .processingEnvironment(processingEnv)
  //                                                               .metaModel(this.metaModel)
  //                                                               .filtersElement(filtersElement)
  //                                                               .build()
  //                                                               .scan(roundEnv);
  //      // check, if the one of the shell in the list is already
  //      // added to the the meta model
  //      // in case it is, remove it.
  //      filterModels.forEach(model -> {
  //        Optional<FilterModel> optional = this.metaModel.getFilters()
  //                                                       .stream()
  //                                                       .filter(s -> model.getFilter()
  //                                                                         .getClassName()
  //                                                                         .equals(s.getFilter()
  //                                                                                  .getClassName()))
  //                                                       .findFirst();
  //        optional.ifPresent(optionalFilter -> this.metaModel.getFilters()
  //                                                           .remove(optionalFilter));
  //      });
  //      // save filter data in metaModel
  //      this.metaModel.getFilters()
  //                    .addAll(filterModels);
  //
  //    }
  //  }
  //
  //  private void handleHandlerAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    for (Element handlerElement : roundEnv.getElementsAnnotatedWith(Handler.class)) {
  //      // validate handler element
  //      HandlerAnnotationValidator.builder()
  //                                .processingEnvironment(processingEnv)
  //                                .roundEnvironment(roundEnv)
  //                                .handlerElement(handlerElement)
  //                                .build()
  //                                .validate();
  //      // scan handler element
  //      HandlerModel handlerModel = HandlerAnnotationScanner.builder()
  //                                                          .processingEnvironment(processingEnv)
  //                                                          .metaModel(this.metaModel)
  //                                                          .handlerElement(handlerElement)
  //                                                          .build()
  //                                                          .scan();
  //      // check, if the handler is already
  //      // added to the the meta model
  //      //
  //      // in case it is, remove it.
  //      final String handlerClassname = handlerModel.getHandler()
  //                                                  .getClassName();
  //      Optional<HandlerModel> optional = this.metaModel.getHandlers()
  //                                                      .stream()
  //                                                      .filter(s -> handlerClassname.equals(s.getHandler()
  //                                                                                            .getClassName()))
  //                                                      .findFirst();
  //      optional.ifPresent(optionalHandler -> this.metaModel.getHandlers()
  //                                                          .remove(optionalHandler));
  //      // save handler data in metaModel
  //      this.metaModel.getHandlers()
  //                    .add(handlerModel);
  //    }
  //  }
  //
  //  private void handleLoggerAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    for (Element loggerElement : roundEnv.getElementsAnnotatedWith(Logger.class)) {
  //      LoggerAnnotationValidator.builder()
  //                               .roundEnvironment(roundEnv)
  //                               .processingEnvironment(processingEnv)
  //                               .loggerElement(loggerElement)
  //                               .build()
  //                               .validate();
  //      // scan filter element and save data in metaModel
  //      this.metaModel = LoggerAnnotationScanner.builder()
  //                                              .processingEnvironment(processingEnv)
  //                                              .metaModel(this.metaModel)
  //                                              .loggerElement(loggerElement)
  //                                              .build()
  //                                              .scan(roundEnv);
  //
  //    }
  //  }
  //
  //  private void handleModuleAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    for (Element moduleElement : roundEnv.getElementsAnnotatedWith(Module.class)) {
  //      // validate application element
  //      ModuleAnnotationValidator.builder()
  //                               .processingEnvironment(processingEnv)
  //                               .moduleElement(moduleElement)
  //                               .build()
  //                               .validate();
  //      // scan application element
  //      ModuleModel moduleModel = ModuleAnnotationScanner.builder()
  //                                                       .processingEnvironment(processingEnv)
  //                                                       .moduleElement(moduleElement)
  //                                                       .build()
  //                                                       .scan(roundEnv);
  //      // store model
  //      this.metaModel.setModuleModel(moduleModel);
  //    }
  //  }
  //
  //  private void handleModulesAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    for (Element modulesElement : roundEnv.getElementsAnnotatedWith(Modules.class)) {
  //      // validate application element
  //      ModulesAnnotationValidator.builder()
  //                                .processingEnvironment(processingEnv)
  //                                .modulesElement(modulesElement)
  //                                .build()
  //                                .validate();
  //      // scan application element
  //      ModulesAnnotationScanner.builder()
  //                              .processingEnvironment(processingEnv)
  //                              .modulesElement(modulesElement)
  //                              .metaModel(metaModel)
  //                              .build()
  //                              .scan(roundEnv);
  //    }
  //  }
  //
  //  private void handleParameterConstraintRule(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    List<ParameterConstraintRuleModel> parameterConstraintRuleModelList = new ArrayList<>();
  //    for (Element parameterConstraintRuleElement : roundEnv.getElementsAnnotatedWith(ParameterConstraintRule.class)) {
  //      // validate
  //      ParameterConstraintRuleAnnotationValidator.builder()
  //                                                .processingEnvironment(processingEnv)
  //                                                .parameterConstraintRuleElement(parameterConstraintRuleElement)
  //                                                .build()
  //                                                .validate();
  //      // create ParameterConstraintRule-Model
  //      ParameterConstraintRuleModel model = ParameterConstraintRuleAnnotationScanner.builder()
  //                                                                                   .parameterConstraintRuleElement(parameterConstraintRuleElement)
  //                                                                                   .build()
  //                                                                                   .scan(roundEnv);
  //      parameterConstraintRuleModelList.add(model);
  //
  //      // create the Impl-class
  //      ParameterConstraintRuleImplGenerator.builder()
  //                                          .metaModel(this.metaModel)
  //                                          .processingEnvironment(processingEnv)
  //                                          .parameterConstraintRuleModel(model)
  //                                          .build()
  //                                          .generate();
  //    }
  //    this.metaModel.setParameterConstraintRules(parameterConstraintRuleModelList);
  //  }
  //
  //  private void handlePopUpControllerAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    List<PopUpControllerModel> popUpControllerModels = new ArrayList<>();
  //    for (Element popUpControllerElement : roundEnv.getElementsAnnotatedWith(PopUpController.class)) {
  //      // validate
  //      PopUpControllerAnnotationValidator.builder()
  //                                        .processingEnvironment(processingEnv)
  //                                        .popUpControllerElement(popUpControllerElement)
  //                                        .build()
  //                                        .validate();
  //      // create PopUpControllerModel
  //      PopUpControllerModel popUpControllerModel = PopUpControllerAnnotationScanner.builder()
  //                                                                                  .processingEnvironment(processingEnv)
  //                                                                                  .metaModel(this.metaModel)
  //                                                                                  .popUpControllerElement(popUpControllerElement)
  //                                                                                  .build()
  //                                                                                  .scan(roundEnv);
  //      // generate PopUpControllerCreator
  //      PopUpControllerCreatorGenerator.builder()
  //                                     .processingEnvironment(processingEnv)
  //                                     .metaModel(this.metaModel)
  //                                     .popUpControllerModel(popUpControllerModel)
  //                                     .build()
  //                                     .generate();
  //      popUpControllerModels.add(popUpControllerModel);
  //    }
  //    // check, if the one of the popUpController in the list is already
  //    // added to the the meta model
  //    //
  //    // in case it is, remove it.
  //    popUpControllerModels.forEach(model -> {
  //      Optional<PopUpControllerModel> optional = this.metaModel.getPopUpControllers()
  //                                                              .stream()
  //                                                              .filter(s -> model.getController()
  //                                                                                .getClassName()
  //                                                                                .equals(s.getController()
  //                                                                                         .getClassName()))
  //                                                              .findFirst();
  //      optional.ifPresent(optionalPopUpControllerModel -> this.metaModel.getPopUpControllers()
  //                                                                       .remove(optionalPopUpControllerModel));
  //    });
  //    // save data in metaModel
  //    this.metaModel.getPopUpControllers()
  //                  .addAll(popUpControllerModels);
  //  }
  //
  //  private void handlePopUpFiltersAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    for (Element popUpFiltersElement : roundEnv.getElementsAnnotatedWith(PopUpFilters.class)) {
  //      // validate filter element
  //      PopUpFiltersAnnotationValidator.builder()
  //                                     .roundEnvironment(roundEnv)
  //                                     .processingEnvironment(processingEnv)
  //                                     .popUpFilterElement(popUpFiltersElement)
  //                                     .build()
  //                                     .validate(popUpFiltersElement);
  //      // scan filter element
  //      List<ClassNameModel> popUpFilterModels = PopUpFiltersAnnotationScanner.builder()
  //                                                                            .processingEnvironment(processingEnv)
  //                                                                            .metaModel(this.metaModel)
  //                                                                            .filtersElement(popUpFiltersElement)
  //                                                                            .build()
  //                                                                            .scan(roundEnv);
  //      // check, if the one of the shell in the list is already
  //      // added to the the meta model
  //      //
  //      // in case it is, remove it.
  //      popUpFilterModels.forEach(model -> {
  //        Optional<ClassNameModel> optional = this.metaModel.getPopUpFilters()
  //                                                          .stream()
  //                                                          .filter(s -> model.getClassName()
  //                                                                            .equals(s.getClassName()))
  //                                                          .findFirst();
  //        optional.ifPresent(optionalFilter -> this.metaModel.getPopUpFilters()
  //                                                           .remove(optionalFilter));
  //      });
  //      // save filter data in metaModel
  //      this.metaModel.getPopUpFilters()
  //                    .addAll(popUpFilterModels);
  //
  //    }
  //  }
  //
  //  private void handleShellAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    List<ShellModel> shellsModels = new ArrayList<>();
  //    for (Element shellElement : roundEnv.getElementsAnnotatedWith(Shell.class)) {
  //      // validate shellCreator!
  //      ShellAnnotationValidator.builder()
  //                              .processingEnvironment(processingEnv)
  //                              .roundEnvironment(roundEnv)
  //                              .build()
  //                              .validate(shellElement);
  //      // generate ShellCreator
  //      ShellModel shellModel = ShellAnnotationScanner.builder()
  //                                                    .processingEnvironment(processingEnv)
  //                                                    .metaModel(this.metaModel)
  //                                                    .shellElement(shellElement)
  //                                                    .build()
  //                                                    .scan(roundEnv);
  //
  //      // Composites-Annotation in controller
  //      shellModel = ShellCompositesAnnotationScanner.builder()
  //                                                   .processingEnvironment(processingEnv)
  //                                                   .shellModel(shellModel)
  //                                                   .shellElement(shellElement)
  //                                                   .build()
  //                                                   .scan(roundEnv);
  //
  //      // generate ShellCreator
  //      ShellCreatorGenerator.builder()
  //                           .processingEnvironment(processingEnv)
  //                           .metaModel(this.metaModel)
  //                           .shellModel(shellModel)
  //                           .build()
  //                           .generate();
  //      shellsModels.add(shellModel);
  //    }
  //    // check, if the one of the shell in the list is already
  //    // added to the the meta model
  //    //
  //    // in case it is, remove it.
  //    shellsModels.forEach(model -> {
  //      Optional<ShellModel> optional = this.metaModel.getShells()
  //                                                    .stream()
  //                                                    .filter(s -> model.getShell()
  //                                                                      .getClassName()
  //                                                                      .equals(s.getShell()
  //                                                                               .getClassName()))
  //                                                    .findFirst();
  //      optional.ifPresent(optionalShellModel -> this.metaModel.getShells()
  //                                                             .remove(optionalShellModel));
  //    });
  //    // save shell data in metaModel
  //    this.metaModel.getShells()
  //                  .addAll(shellsModels);
  //  }
  //
  //  private void handleTrackerAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    //TODO why to iterate over different Tackers?  It should be only one element?
  //    for (Element trackerElement : roundEnv.getElementsAnnotatedWith(Tracker.class)) {
  //      // validate filter element
  //      TrackerAnnotationValidator.builder()
  //                                .roundEnvironment(roundEnv)
  //                                .processingEnvironment(processingEnv)
  //                                .trackerElement(trackerElement)
  //                                .build()
  //                                .validate();
  //
  //      // scan tracker element and save data in metaModel
  //      TrackerModel trackerModel = TrackerAnnotationScanner.builder()
  //                                                          .processingEnvironment(processingEnv)
  //                                                          .trackerElement(trackerElement)
  //                                                          .build()
  //                                                          .scan(roundEnv);
  //
  //      this.metaModel.setTracker(trackerModel);
  //    }
  //  }
  //
  //  private void handleVersionAnnotation(RoundEnvironment roundEnv)
  //      throws ProcessorException {
  //    for (Element trackerElement : roundEnv.getElementsAnnotatedWith(Version.class)) {
  //      // validate filter element
  //      VersionAnnotationValidator.builder()
  //                                .roundEnvironment(roundEnv)
  //                                .processingEnvironment(processingEnv)
  //                                .versionElement(trackerElement)
  //                                .build()
  //                                .validate();
  //      // scan version element and save data in metaModel
  //      this.metaModel = VersionAnnotationScanner.builder()
  //                                               .metaModel(this.metaModel)
  //                                               .versionElement(trackerElement)
  //                                               .build()
  //                                               .scan();
  //
  //    }
  //  }

  private void writeFile(Element element,
                         String postFix,
                         TypeSpec.Builder typeSpec)
      throws ProcessorException {
    JavaFile javaFile = JavaFile.builder(this.processorUtils.getPackageAsString(element),
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(this.processingEnv.getFiler());
      System.out.println(element.toString());
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" +
                                   element.toString() +
                                   postFix +
                                   "<< -> exception: " +
                                   e.getMessage());
    }
  }

  private String createConstraintClassName(String modelName,
                                           String fieldName,
                                           String postFix) {
    return this.processorUtils.setFirstCharacterToUpperCase(modelName) +
           "_" +
           this.processorUtils.setFirstCharacterToUpperCase(fieldName) +
           "_" +
           postFix;
  }

  private String createValidatorClassName(String modelName) {
    return this.processorUtils.setFirstCharacterToUpperCase(modelName) + MalioProcessor.MALIO_VALIDATOR_IMPL_NAME;
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(processingEnv)
                                        .build();
    //    // get stored Meta Model and use it, if there is one!
    //    MetaModel restoredModel = this.restore();
    //    if (!Objects.isNull(restoredModel)) {
    //      this.metaModel = restoredModel;
    //    }
  }

  //  private MetaModel restore() {
  //    Gson gson = new Gson();
  //    try {
  //      FileObject resource = processingEnv.getFiler()
  //                                         .getResource(StandardLocation.CLASS_OUTPUT,
  //                                                      "",
  //                                                      this.createRelativeFileName());
  //      return gson.fromJson(resource.getCharContent(true)
  //                                   .toString(),
  //                           MetaModel.class);
  //    } catch (IOException e) {
  //      // every thing is ok -> no operation
  //      return null;
  //    }
  //  }
  //
  //  private String createRelativeFileName() {
  //    return ProcessorConstants.META_INF + "/" + ProcessorConstants.NALU_FOLDER_NAME + "/" + NaluProcessor.APPLICATION_PROPERTIES;
  //  }

}
