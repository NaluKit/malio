package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractWhitelistConstraint
    extends AbstractConstraint<String> {

  private String message;

  private List<String> whitelist;

  public AbstractWhitelistConstraint(String packageName,
                                     String simpleName,
                                     String fieldName,
                                     String[] whitelist) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
    this.whitelist = Arrays.asList(whitelist);
  }

  public void check(String value) throws MalioValidationException {
    if (value != null && !whitelist.contains(value)) {
      throw new MalioValidationException(this.message);
    }
  }

  public void isValid(String value, ValidationResult validationResult) {
    if (value != null &&  !whitelist.contains(value)) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
