package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

import java.util.Objects;

public abstract class AbstractNotNullConstraint<T>
    extends AbstractConstraint<T> {

  private String message;

  public AbstractNotNullConstraint(String packageName,
                                   String simpleName,
                                   String fieldName) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
  }

  public void check(T value) throws MalioValidationException {
    if (Objects.isNull(value)) {
      throw new MalioValidationException(this.message);
    }
  }

  public void  isValid(T value, ValidationResult validationResult) {
    if (Objects.isNull(value)) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
