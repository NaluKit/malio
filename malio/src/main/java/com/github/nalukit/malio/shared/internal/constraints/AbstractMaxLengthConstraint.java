package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

public abstract class AbstractMaxLengthConstraint
    extends AbstractConstraint<String> {

  private String message;

  private int maxLength;

  public AbstractMaxLengthConstraint(String packageName,
                                     String simpleName,
                                     String fieldName,
                                     int maxLength) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
    this.maxLength = maxLength;
  }

  public void check(String value) throws MalioValidationException {
    if (value != null && value.length() > maxLength) {
      throw new MalioValidationException(this.message);
    }
  }

  public void isValid(String value, ValidationResult validationResult) {
    if (value != null && value.length() > maxLength) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
