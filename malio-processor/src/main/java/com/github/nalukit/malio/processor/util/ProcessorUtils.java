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
package com.github.nalukit.malio.processor.util;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProcessorUtils {

  private final Messager              messager;
  private final ProcessingEnvironment processingEnvironment;

  @SuppressWarnings("unused")
  private ProcessorUtils(Builder builder) {
    super();

    this.processingEnvironment = builder.processingEnvironment;
    this.messager              = builder.processingEnvironment.getMessager();
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getPackageAsString(Element type) {
    return this.getPackage(type)
               .getQualifiedName()
               .toString();
  }

  /**
   * Returns all of the superclasses and superinterfaces for a given generator
   * including the generator itself. The returned set maintains an internal
   * breadth-first ordering of the generator, followed by its interfaces (and their
   * super-interfaces), then the supertype and its interfaces, and so on.
   *
   * @param types      types
   * @param typeMirror of the class to check
   * @return Set of implemented super types
   */
  public Set<TypeMirror> getFlattenedSupertypeHierarchy(Types types,
                                                        TypeMirror typeMirror) {
    List<TypeMirror>          toAdd  = new ArrayList<>();
    LinkedHashSet<TypeMirror> result = new LinkedHashSet<>();
    toAdd.add(typeMirror);
    for (int i = 0; i < toAdd.size(); i++) {
      TypeMirror type = toAdd.get(i);
      if (result.add(type)) {
        toAdd.addAll(types.directSupertypes(type));
      }
    }
    return result;
  }

  public void createErrorMessage(String errorMessage) {
    StringWriter sw = new StringWriter();
    PrintWriter  pw = new PrintWriter(sw);
    pw.println(errorMessage);
    pw.close();
    messager.printMessage(Diagnostic.Kind.ERROR,
                          sw.toString());

  }

  public void createErrorMessage(Exception e) {
    StringWriter sw = new StringWriter();
    PrintWriter  pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    pw.close();
    messager.printMessage(Diagnostic.Kind.ERROR,
                          sw.toString());

  }

  public PackageElement getPackage(Element type) {
    while (type.getKind() != ElementKind.PACKAGE) {
      type = type.getEnclosingElement();
    }
    return (PackageElement) type;
  }

  public void createNoteMessage(String noteMessage) {
    StringWriter sw = new StringWriter();
    PrintWriter  pw = new PrintWriter(sw);
    pw.println(noteMessage);
    pw.close();
    messager.printMessage(Diagnostic.Kind.NOTE,
                          sw.toString());
  }

  public String setFirstCharacterToUpperCase(String value) {
    return value.substring(0,
                           1)
                .toUpperCase() + value.substring(1);
  }

  public String createGetMethodName(String value) {
    return "get" + value.substring(0,
                                   1)
                        .toUpperCase() + value.substring(1);
  }

  public String createStringInitializationFromArray(String[] array) {
    String[] newArray = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      newArray[i] = String.format("\"%s\"",
                                  array[i]);
    }
    return String.format("new String[]{%s}",
                         String.join(", ",
                                     newArray));
  }

  public <A extends Annotation> List<Element> getVariablesFromTypeElementAnnotatedWith(ProcessingEnvironment processingEnvironment,
                                                                                       TypeElement element,
                                                                                       Class<A> annotation) {
    return processingEnvironment.getElementUtils()
                                .getAllMembers(element)
                                .stream()
                                .filter(e -> e.getKind() == ElementKind.FIELD)
                                .filter(e -> e.getAnnotation(annotation) != null)
                                .collect(Collectors.toList());
  }

  public boolean checkPrimitiveDataType(VariableElement variableElement,
                                        TypeKind... typeKind) {
    TypeMirror type = variableElement.asType();
    if (!type.getKind()
             .isPrimitive()) {
      return false;
    }

    return Arrays.asList(typeKind)
                 .contains(type.getKind());
  }

  private List<TypeMirror> getAllSuperTypes(TypeMirror type,
                                            Types types) {
    List<TypeMirror>           allSuperTypes    = new ArrayList<>();
    List<? extends TypeMirror> directSuperTypes = types.directSupertypes(type);
    for (TypeMirror directSuperType : directSuperTypes) {
      if (!allSuperTypes.contains(directSuperType)) {
        allSuperTypes.add(directSuperType);
        allSuperTypes.addAll(getAllSuperTypes(directSuperType,
                                              types));
      }
    }

    return allSuperTypes;
  }

  public boolean checkDeclaredDataType(VariableElement variableElement,
                                       Class<?>... classes) {
    DeclaredType typeToCheck = (DeclaredType) variableElement.asType();
    Elements     elements    = this.processingEnvironment.getElementUtils();
    Types        types       = this.processingEnvironment.getTypeUtils();

    for (Class<?> c : classes) {
      DeclaredType type = (DeclaredType) elements.getTypeElement(c.getName())
                                                 .asType();

      if (type.asElement()
              .equals(typeToCheck.asElement())) {
        return true;
      }
    }

    List<TypeMirror> typeMirrors = getAllSuperTypes(typeToCheck,
                                                    types);
    for (TypeMirror superType : typeMirrors) {
      DeclaredType declaredSuperType = (DeclaredType) superType;

      for (Class<?> c : classes) {
        DeclaredType type = (DeclaredType) elements.getTypeElement(c.getName())
                                                   .asType();

        if (type.asElement()
                .equals(declaredSuperType.asElement())) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean checkDataType(VariableElement variableElement,
                               List<TypeKind> typeKind,
                               List<Class<?>> classes) {
    if (variableElement.asType()
                       .getKind()
                       .isPrimitive()) {
      if (typeKind == null) {
        return false;
      }
      return checkPrimitiveDataType(variableElement,
                                    typeKind.toArray(new TypeKind[] {}));
    }
    if (classes == null) {
      return false;
    }
    return checkDeclaredDataType(variableElement,
                                 classes.toArray(new Class<?>[] {}));
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public ProcessorUtils build() {
      return new ProcessorUtils(this);
    }

  }

}
