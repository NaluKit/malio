package com.github.nalukit.malio.processor.model;

public class ValidatorModel {

  private String packageName;
  private String simpleClassName;
  private String fieldName;
  private String postFix;
  private Type   type;

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

  public enum Type {
    LIST,
    MAP,
    NATIVE
  }
}
