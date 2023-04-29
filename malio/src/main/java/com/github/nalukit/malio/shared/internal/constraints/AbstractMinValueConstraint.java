package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

public abstract class AbstractMinValueConstraint
    extends AbstractConstraint<Number> {

  private String message;

  private Long minValue;


  public AbstractMinValueConstraint(String packageName,
                                    String simpleName,
                                    String fieldName,
                                    Number minValue) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
    this.minValue = minValue.longValue();
  }

  public void check(Number value) throws MalioValidationException {
    if (value != null && value.longValue() < this.minValue) {
      throw new MalioValidationException(this.message);
    }
  }

  public void isValid(Number value, ValidationResult validationResult) {
    if (value != null && value.longValue() < this.minValue) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
