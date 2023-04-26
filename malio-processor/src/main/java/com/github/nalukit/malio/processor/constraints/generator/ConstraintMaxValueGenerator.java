package com.github.nalukit.malio.processor.constraints.generator;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.ProcessorException;
import com.github.nalukit.malio.processor.exceptions.UnsupportedTypeException;
import com.github.nalukit.malio.processor.util.BuildWithMalioCommentProvider;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.MaxValue;
import com.github.nalukit.malio.shared.internal.constraints.AbstractMaxValueConstraint;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Arrays;

public class ConstraintMaxValueGenerator
    extends AbstractGenerator {
  private Element         validatorElement;
  private VariableElement variableElement;

  private ConstraintMaxValueGenerator(Builder builder) {
    this.validatorElement = builder.validatorElement;
    this.variableElement  = builder.variableElement;
    this.elements         = builder.elements;
    this.types            = builder.types;
    this.filer            = builder.filer;
    this.processorUtils   = builder.processorUtils;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
      throws ProcessorException {

    if (!processorUtils.checkDataType(variableElement,
            Arrays.asList(TypeKind.INT, TypeKind.LONG),
            Arrays.asList(Integer.class, Long.class))) {
      throw new UnsupportedTypeException("Type '" + variableElement.asType()  + "' not supported for " + getClass().getSimpleName());
    }


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
                                               variableElement.getAnnotation(MaxValue.class)
                                                              .value())

                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("getErrorMessage")
                                 .addModifiers(Modifier.PROTECTED)
                                 .addAnnotation(ClassName.get(Override.class))
                                 .returns(ClassName.get(String.class))
                                 .addStatement("return \"noch mit error messages aus Properties ersetzen (wegen locale und so) ....\"")
                                 .build());
    super.writeFile(variableElement,
                    Constants.MALIO_CONSTRAINT_MAXVALUE_IMPL_NAME,
                    typeSpec);
  }

  private TypeSpec.Builder createConstraintTypeSpec(Element validatorElement,
                                                    VariableElement variableElement) {
    return TypeSpec.classBuilder(this.createConstraintClassName(validatorElement.getSimpleName()
                                                                                .toString(),
                                                                variableElement.getSimpleName()
                                                                               .toString(),
                                                                Constants.MALIO_CONSTRAINT_MAXVALUE_IMPL_NAME))
                   .addJavadoc(BuildWithMalioCommentProvider.INSTANCE.getGeneratedComment())
                   .superclass(ClassName.get(AbstractMaxValueConstraint.class))
                   .addModifiers(Modifier.PUBLIC,
                                 Modifier.FINAL);
  }

  public static class Builder {

    Element         validatorElement;
    VariableElement variableElement;
    Elements        elements;
    Types           types;
    Filer           filer;
    ProcessorUtils  processorUtils;

    public Builder validatorElement(Element validatorElement) {
      this.validatorElement = validatorElement;
      return this;
    }

    public Builder variableElement(Element variableElement) {
      this.variableElement = (VariableElement) variableElement;
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

    public ConstraintMaxValueGenerator build() {
      return new ConstraintMaxValueGenerator(this);
    }
  }
}
