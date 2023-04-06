package com.github.nalukit.malio.processor.model;

public class ConstraintModel {

  private String packageName;
  private String simpleClassName;
  private String fieldName;
  private String postFix;
  private ValidatorType validatorType;

  public ConstraintModel(String packageName,
                         String simpleClassName,
                         String fieldName,
                         String postFix,
                         ValidatorType validatorType) {
    this.packageName     = packageName;
    this.simpleClassName = simpleClassName;
    this.fieldName       = fieldName;
    this.postFix         = postFix;
    this.validatorType   = validatorType;
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

  public ValidatorType getValidatorType() {
    return validatorType;
  }
}
