package com.github.nalukit.malio.processor.constraints.generator;

import com.github.nalukit.malio.processor.ProcessorException;
import com.github.nalukit.malio.processor.constraints.AbstractConstraint;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;

public abstract class AbstractGenerator implements IsGenerator {

  protected Elements       elements;
  protected Types          types;
  protected Filer filer;
  protected ProcessorUtils processorUtils;

  public AbstractGenerator() {
  }

  protected String createConstraintClassName(String modelName,
                                           String fieldName,
                                           String postFix) {
    return modelName +
           "_" +
           this.processorUtils.setFirstCharacterToUpperCase(fieldName) +
           "_" +
           postFix;
  }

  protected void writeFile(Element element,
                         String postFix,
                         TypeSpec.Builder typeSpec)
      throws ProcessorException {
    JavaFile javaFile = JavaFile.builder(this.processorUtils.getPackageAsString(element),
                                         typeSpec.build())
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
