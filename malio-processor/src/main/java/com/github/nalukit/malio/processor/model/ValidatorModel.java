package com.github.nalukit.malio.processor.model;

import javax.lang.model.element.TypeElement;

public class ValidatorModel {

  private String      packageName;
  private String      simpleClassName;
  private String      fieldName;
  private String      postFix;
  private Type        type;
  private TypeElement genericTypeElement01;
  private TypeElement genericTypeElement02;

  public ValidatorModel(String packageName,
                        String simpleClassName,
                        String fieldName,
                        String postFix,
                        Type type) {
    this.packageName     = packageName;
    this.simpleClassName = simpleClassName;
    this.fieldName       = fieldName;
    this.postFix         = postFix;
    this.type            = type;
  }

  public ValidatorModel(String packageName,
                        String simpleClassName,
                        String fieldName,
                        String postFix,
                        Type type,
                        TypeElement genericTypeElement01) {
    this(packageName, simpleClassName, fieldName, postFix, type);
    this.genericTypeElement01 = genericTypeElement01;
  }

  public ValidatorModel(String packageName,
                        String simpleClassName,
                        String fieldName,
                        String postFix,
                        Type type,
                        TypeElement genericTypeElement01,
                        TypeElement genericTypeElement02) {
    this(packageName, simpleClassName, fieldName, postFix, type, genericTypeElement01);
    this.genericTypeElement02 = genericTypeElement02;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getSimpleClassName() {
    return simpleClassName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getPostFix() {
    return postFix;
  }

  public Type getType() {
    return type;
  }

  public TypeElement getGenericTypeElement01() {
    return genericTypeElement01;
  }

  public TypeElement getGenericTypeElement02() {
    return genericTypeElement02;
  }

  public enum Type {
    LIST,
    MAP,
    NATIVE
  }
}
