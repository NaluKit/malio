package com.github.nalukit.malio.processor;

import com.github.nalukit.malio.processor.generator.ConstraintMaxLengthGenerator;
import com.github.nalukit.malio.processor.generator.ConstraintNotNullGenerator;
import com.github.nalukit.malio.processor.generator.ValidatorGenerator;
import com.github.nalukit.malio.processor.model.ConstraintModel;
import com.github.nalukit.malio.processor.model.ValidatorModel;
import com.github.nalukit.malio.processor.scanner.ConstraintMaxLengthScanner;
import com.github.nalukit.malio.processor.scanner.ConstraintNotNullScanner;
import com.github.nalukit.malio.processor.scanner.ValidatorScanner;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.Malio;
import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.MaxLength;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@AutoService(Processor.class)
public class MalioProcessor
    extends AbstractProcessor {

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
                List<ConstraintModel> constraintList = new ArrayList<>();
                Set<TypeMirror> mirrors = this.processorUtils.getFlattenedSupertypeHierarchy(this.processingEnv.getTypeUtils(),
                                                                                             validatorElement.asType());
                // handle malio annotations ...
                for (TypeMirror mirror : mirrors) {
                  Element element = this.processingEnv.getTypeUtils()
                                                      .asElement(mirror);
                  // Not NUll Constraint
                  this.createConstraintNotNull(element,
                                               constraintList);
                  // MaxLength Constraint (String only)
                  this.createConstraintMaxLength(element,
                                                 constraintList);

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

                this.createValidator(validatorElement,
                          constraintList);
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
