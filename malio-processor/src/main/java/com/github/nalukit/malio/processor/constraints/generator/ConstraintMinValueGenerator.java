package com.github.nalukit.malio.processor.constraints.generator;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.ProcessorException;
import com.github.nalukit.malio.processor.constraints.AbstractConstraint;
import com.github.nalukit.malio.processor.util.BuildWithMalioCommentProvider;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.MinValue;
import com.github.nalukit.malio.shared.internal.constraints.AbstractMinValueConstraint;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ConstraintMinValueGenerator
    extends AbstractGenerator {

  private AbstractConstraint<MinValue> constraint;

  private ConstraintMinValueGenerator(Builder builder) {
    this.elements         = builder.elements;
    this.types            = builder.types;
    this.filer            = builder.filer;
    this.processorUtils   = builder.processorUtils;
    this.constraint = builder.constraint;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate(Element validatorElement, VariableElement variableElement)
      throws ProcessorException {
    TypeSpec.Builder typeSpec = createConstraintTypeSpec(validatorElement,
                                                         variableElement);
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addModifiers(Modifier.PUBLIC)
                                 .addStatement("super($S, $S, $S, Long.valueOf($L))",
                                               this.processorUtils.getPackage(variableElement),
                                               this.processorUtils.setFirstCharacterToUpperCase(variableElement.getEnclosingElement()
                                                                                                               .getSimpleName()
                                                                                                               .toString()),
                                               variableElement.getSimpleName()
                                                              .toString(),
                                               variableElement.getAnnotation(MinValue.class)
                                                              .value())

                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("getErrorMessage")
                                 .addModifiers(Modifier.PROTECTED)
                                 .addAnnotation(ClassName.get(Override.class))
                                 .returns(ClassName.get(String.class))
                                 .addStatement("return \"noch mit error messages aus Properties ersetzen (wegen locale und so) ....\"")
                                 .build());
    super.writeFile(variableElement,
            constraint.getImplementationName(),
                    typeSpec);
  }

  private TypeSpec.Builder createConstraintTypeSpec(Element validatorElement,
                                                    VariableElement variableElement) {
    return TypeSpec.classBuilder(this.createConstraintClassName(validatorElement.getSimpleName()
                                                                                .toString(),
                                                                variableElement.getSimpleName()
                                                                               .toString(),
                    constraint.getImplementationName()))
                   .addJavadoc(BuildWithMalioCommentProvider.INSTANCE.getGeneratedComment())
                   .superclass(constraint.getValidationClass(variableElement))
                   .addModifiers(Modifier.PUBLIC,
                                 Modifier.FINAL);
  }

  public static class Builder {

    Elements        elements;
    Types           types;
    Filer           filer;
    ProcessorUtils  processorUtils;
    AbstractConstraint<MinValue> constraint;

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

    public Builder constraint(AbstractConstraint<MinValue> constraint) {
      this.constraint = constraint;
      return this;
    }

    public ConstraintMinValueGenerator build() {
      return new ConstraintMinValueGenerator(this);
    }
  }
}
