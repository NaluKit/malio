package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

import java.util.Collection;
import java.util.Objects;

public abstract class AbstractSizeConstraint<T extends Collection>
    extends AbstractConstraint<T> {

  private String message;
  private int min;
  private int max;

  public AbstractSizeConstraint(String packageName,
                                String simpleName,
                                String fieldName,
                                int min,
                                int max) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
    this.min = min;
    this.max = max;
  }

  public void check(T value) throws MalioValidationException {
    if (Objects.nonNull(value)) {
      int size = value.size();

      if (size < this.min || size > this.max) {
        throw new MalioValidationException(this.message);
      }
    }
  }

  public void  isValid(T value, ValidationResult validationResult) {
    if (Objects.nonNull(value)) {
      int size = value.size();

      if (size < this.min || size > this.max) {
        validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
      }
    }
  }
}
