package com.github.nalukit.malio.shared.internal.constraints;

public abstract class AbstractConstraint<T> implements IsMalioConstraint<T> {

  private String packageName;
  private String simpleName;
  private String fieldName;

  private AbstractConstraint() {
  }

  public AbstractConstraint(String packageName,
                            String simpleName,
                            String fieldName) {
    this.packageName = packageName;
    this.simpleName  = simpleName;
    this.fieldName   = fieldName;
  }

  protected String getPackageName() {
    return packageName;
  }

  protected String getSimpleName() {
    return simpleName;
  }

  protected String getFieldName() {
    return fieldName;
  }

  protected String getClassName() {
    return this.packageName + "." + this.simpleName;
  }

  protected abstract String getErrorMessage();

}
