package com.github.nalukit.malio.processor.util;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProcessorUtils {

  private final Messager messager;


  @SuppressWarnings("unused")
  private ProcessorUtils(Builder builder) {
    super();

    ProcessingEnvironment processingEnvironment = builder.processingEnvironment;

    this.messager = processingEnvironment.getMessager();
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
