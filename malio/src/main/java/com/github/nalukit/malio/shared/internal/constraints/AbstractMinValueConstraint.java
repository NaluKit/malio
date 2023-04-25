package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.NumberComparator;

public abstract class AbstractMinValueConstraint<T extends Number & Comparable>
    extends AbstractConstraint<T> {

  private String message;

  private T minValue;

  private NumberComparator<T> comparator = new NumberComparator<>();

  public AbstractMinValueConstraint(String packageName,
                                    String simpleName,
                                    String fieldName,
                                    T minValue) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
    this.minValue = minValue;
  }

  public void check(T value) throws MalioValidationException {
    if (value != null &&  comparator.compare(value, this.minValue) < 0) {
      throw new MalioValidationException(this.message);
    }
  }

  public void isValid(T value, ValidationResult validationResult) {
    if (value != null && comparator.compare(value, this.minValue) < 0) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
