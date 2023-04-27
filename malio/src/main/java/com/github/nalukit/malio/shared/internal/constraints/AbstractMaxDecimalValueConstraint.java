package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

import java.math.BigDecimal;


public abstract class AbstractMaxDecimalValueConstraint
    extends AbstractConstraint<BigDecimal> {

  private String message;
  private BigDecimal maxValue;

  public AbstractMaxDecimalValueConstraint(String packageName,
                                           String simpleName,
                                           String fieldName,
                                           String maxValue) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
    this.maxValue = new BigDecimal(maxValue);
  }

  public void check(BigDecimal value) throws MalioValidationException {
    if (value != null &&  value.compareTo(this.maxValue) > 0) {
      throw new MalioValidationException(this.message);
    }
  }

  public void isValid(BigDecimal value, ValidationResult validationResult) {
    if (value != null && value.compareTo(this.maxValue) > 0) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
