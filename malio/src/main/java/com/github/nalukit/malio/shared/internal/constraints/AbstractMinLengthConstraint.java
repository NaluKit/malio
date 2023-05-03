package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

public abstract class AbstractMinLengthConstraint
    extends AbstractConstraint<String> {

  private String message;

  private int minLength;

  public AbstractMinLengthConstraint(String packageName,
                                     String simpleName,
                                     String fieldName,
                                     int minLength) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
    this.minLength = minLength;
  }

  public void check(String value) throws MalioValidationException {
    if (value != null && value.length() < minLength) {
      throw new MalioValidationException(this.message);
    }
  }

  public void isValid(String value, ValidationResult validationResult) {
    if (value != null && value.length() < minLength) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
