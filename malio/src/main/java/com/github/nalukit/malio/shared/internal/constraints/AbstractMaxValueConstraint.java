package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;


public abstract class AbstractMaxValueConstraint
    extends AbstractConstraint<Number> {

  private String message;
  private Long maxValue;

  public AbstractMaxValueConstraint(String packageName,
                                    String simpleName,
                                    String fieldName,
                                    Number maxValue) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
    this.maxValue = maxValue.longValue();
  }

  public void check(Number value) throws MalioValidationException {
    if (value != null &&  value.longValue() > this.maxValue) {
      throw new MalioValidationException(this.message);
    }
  }

  public void isValid(Number value, ValidationResult validationResult) {
    if (value != null && value.longValue() > this.maxValue) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
