package com.github.nalukit.malio.processor.model;

public class ConstraintModel {

  private String packageName;
  private String simpleClassName;
  private String fieldName;
  private String         postFix;
  private ConstraintType constraintType;

  public ConstraintModel(String packageName,
                         String simpleClassName,
                         String fieldName,
                         String postFix,
                         ConstraintType constraintType) {
    this.packageName     = packageName;
    this.simpleClassName = simpleClassName;
    this.fieldName       = fieldName;
    this.postFix         = postFix;
    this.constraintType  = constraintType;
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

  public ConstraintType getValidatorType() {
    return constraintType;
  }
}
