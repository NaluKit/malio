package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

import java.util.Collection;
import java.util.Objects;

public abstract class AbstractNotEmptyConstraint<T extends Collection<?>>
    extends AbstractConstraint<T> {

  private final String message;

  public AbstractNotEmptyConstraint(String packageName,
                                    String simpleName,
                                    String fieldName) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
  }

  public void check(T value) throws MalioValidationException {
    if (Objects.nonNull(value) && value.isEmpty()) {
      throw new MalioValidationException(this.message);
    }
  }

  public void  isValid(T value, ValidationResult validationResult) {
    if (Objects.nonNull(value)  && value.isEmpty()) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
