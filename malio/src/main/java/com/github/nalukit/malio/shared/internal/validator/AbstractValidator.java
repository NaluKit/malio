package com.github.nalukit.malio.shared.internal.validator;

import com.github.nalukit.malio.shared.model.ValidationResult;

public abstract class AbstractValidator {

  protected ValidationResult        validationResult;

  public AbstractValidator() {
    this.validationResult = new ValidationResult();
  }

}
