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
package com.github.nalukit.malio.processor.constraints.generator;

import com.github.nalukit.malio.processor.ProcessorException;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;

public abstract class AbstractGenerator
    implements IsGenerator {

  protected Elements       elements;
  protected Types          types;
  protected Filer          filer;
  protected ProcessorUtils processorUtils;

  public AbstractGenerator() {
  }

  protected String createConstraintClassName(String modelName,
                                             String fieldName,
                                             String postFix) {
    return modelName + "_" + this.processorUtils.setFirstCharacterToUpperCase(fieldName) + "_" + postFix;
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
